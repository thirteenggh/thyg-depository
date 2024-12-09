package org.sonatype.nexus.orient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.sonatype.goodies.testsupport.TestSupport;

import com.orientechnologies.orient.core.command.OCommandOutputListener;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Trials of using OrientDB backup and restore functions.
 */
public class BackupRestoreTrial
    extends TestSupport
{
  static {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
  }

  private File basedir;

  @Before
  public void setUp() throws Exception {
    this.basedir = util.createTempDir("db");
  }

  private ODatabaseDocumentTx createDatabase(final String name) {
    File dir = new File(basedir, name);
    return new ODatabaseDocumentTx("plocal:" + dir).create();
  }

  private ODatabaseDocumentTx openDatabase(final String name) {
    File dir = new File(basedir, name);
    return new ODatabaseDocumentTx("plocal:" + dir).open("admin", "admin");
  }

  private ODocument createPerson(final ODatabaseDocumentTx db) {
    ODocument doc = db.newInstance("Person");
    doc.field("name", "Luke");
    doc.field("surname", "Skywalker");
    doc.field("city", new ODocument("City")
        .field("name", "Rome")
        .field("country", "Italy"));
    doc.save();
    return doc;
  }

  @Test
  public void backupDatabase() throws Exception {
    try (ODatabaseDocumentTx db = createDatabase("test")) {
      assertThat(createPerson(db), notNullValue());

      // Backup makes ZIP files
      File file = File.createTempFile("export-", ".zip", util.getTmpDir());
      log("Exporting to: {}", file);

      try (OutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
        OCommandOutputListener listener = new OCommandOutputListener()
        {
          @Override
          public void onMessage(final String text) {
            log("> {}", text.trim());
          }
        };
        db.backup(output, null, null, listener, 9, 16384);
      }
    }
  }
}
