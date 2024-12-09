package org.sonatype.nexus.internal.orient;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.io.DirectoryHelper;
import org.sonatype.nexus.jmx.reflect.ManagedAttribute;
import org.sonatype.nexus.jmx.reflect.ManagedObject;
import org.sonatype.nexus.orient.DatabaseExternalizer;
import org.sonatype.nexus.orient.DatabaseExternalizerImpl;
import org.sonatype.nexus.orient.DatabaseRestorer;
import org.sonatype.nexus.orient.DatabaseManager;
import org.sonatype.nexus.orient.DatabaseManagerSupport;

import com.google.common.annotations.VisibleForTesting;
import com.orientechnologies.common.io.OFileUtils;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@code plocal} {@link DatabaseManager} implementation.
 *
 * @since 3.0
 */
@Named
@Singleton
@ManagedObject
public class DatabaseManagerImpl
    extends DatabaseManagerSupport
{
  public static final String WORK_PATH = "db";

  private final File databasesDirectory;

  private final DatabaseRestorer databaseRestorer;

  @Inject
  public DatabaseManagerImpl(final ApplicationDirectories applicationDirectories,
                             final DatabaseRestorer databaseRestorer) {
    checkNotNull(applicationDirectories);
    this.databasesDirectory = applicationDirectories.getWorkDirectory(WORK_PATH);
    log.debug("Databases directory: {}", databasesDirectory);
    this.databaseRestorer = checkNotNull(databaseRestorer);
  }

  @VisibleForTesting
  public DatabaseManagerImpl(final File databasesDirectory,
                             final DatabaseRestorer databaseRestorer) {
    this.databasesDirectory = checkNotNull(databasesDirectory);
    log.debug("Databases directory: {}", databasesDirectory);
    this.databaseRestorer = checkNotNull(databaseRestorer);
  }

  @ManagedAttribute
  public File getDatabasesDirectory() {
    return databasesDirectory;
  }

  /**
   * Returns the directory for the given named database.  Directory may or may not exist.
   */
  private File directory(final String name) throws IOException {
    return new File(databasesDirectory, name).getCanonicalFile();
  }

  @Override
  protected String connectionUri(final String name) {
    try {
      File dir = directory(name);
      DirectoryHelper.mkdir(dir);

      // OHazelcastPlugin.onOpen() assumes that dbUri.startsWith("plocal:" + dbDirectory)
      // We're well advised to meet that assumption or clustering won't work, more specifically we need to form
      // plocal:/data/dbName for Unix/OSX and plocal:D:/data/dbName for Win (no slash before drive letter)
      return "plocal:" + OFileUtils.getPath(dir.getAbsolutePath()).replace("//", "/");
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * When the database is being created, maybe import from the standard export location or restore backups.
   *
   * @see DatabaseExternalizer#EXPORT_FILENAME
   * @see DatabaseExternalizer#EXPORT_GZ_FILENAME
   * @see DatabaseExternalizerImpl#maybeImportFromStandardLocation(ODatabaseDocumentTx, File)
   */
  @Override
  protected void created(final ODatabaseDocumentTx db, final String name) throws Exception {
    File dir = directory(name);
    if (!databaseRestorer.maybeRestoreDatabase(db, name)) {
      DatabaseExternalizerImpl externalizer = externalizer(name);
      externalizer.maybeImportFromStandardLocation(db, dir);
    }
  }
}
