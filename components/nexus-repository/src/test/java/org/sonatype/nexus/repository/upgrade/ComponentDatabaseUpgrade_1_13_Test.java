package org.sonatype.nexus.repository.upgrade;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.orient.OClassNameBuilder;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ComponentDatabaseUpgrade_1_13_Test
    extends TestSupport
{
  static final String DB_CLASS = new OClassNameBuilder().type("assetdownloadcount").build();

  @Rule
  public DatabaseInstanceRule componentDatabase = DatabaseInstanceRule.inMemory("test_component");

  private ComponentDatabaseUpgrade_1_13 underTest;

  @Before
  public void setUp() {
    underTest = new ComponentDatabaseUpgrade_1_13(componentDatabase.getInstanceProvider());
  }

  @Test
  public void testClassDropped() throws Exception {
    try (ODatabaseDocumentTx db = componentDatabase.getInstance().connect()) {
      OSchema schema = db.getMetadata().getSchema();
      schema.createClass(DB_CLASS);
      db.browseClass(DB_CLASS);
    }

    underTest.apply();

    try (ODatabaseDocumentTx db = componentDatabase.getInstance().connect()) {
      try {
        db.browseClass(DB_CLASS);
        fail("Expected exception thrown");
      }
      catch (IllegalArgumentException e) {
        assertThat(e.getMessage(), is("Class 'assetdownloadcount' not found in current database"));
      }
    }
  }

  @SuppressWarnings("java:S2699") // sonar wants assertions, but seems best to let an exception bubble up
  @Test
  public void testWithoutExistingDb() throws Exception {
    underTest.apply();
  }

}
