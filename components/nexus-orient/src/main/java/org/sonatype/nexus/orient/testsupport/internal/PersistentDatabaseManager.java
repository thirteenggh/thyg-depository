package org.sonatype.nexus.orient.testsupport.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.sonatype.nexus.common.io.DirectoryHelper;
import org.sonatype.nexus.orient.DatabaseManager;
import org.sonatype.nexus.orient.DatabaseManagerSupport;

import com.orientechnologies.common.io.OFileUtils;

/**
 * File-based {@link DatabaseManager} implementation.
 * 
 * @since 3.1
 */
public class PersistentDatabaseManager
    extends DatabaseManagerSupport
{
  private static final Path BASEDIR = new File(System.getProperty("basedir", "")).toPath();

  private final File databasesDirectory;

  public PersistentDatabaseManager() throws IOException {
    databasesDirectory = Files.createTempDirectory(BASEDIR.resolve("target"), "test-db.").toFile();
    log.info("Database dir: {}", databasesDirectory);
  }

  /**
   * Returns the directory for the given named database. Directory may or may not exist.
   */
  private File directory(final String name) throws IOException {
    return new File(databasesDirectory, name).getCanonicalFile();
  }

  @Override
  protected String connectionUri(final String name) {
    try {
      File dir = directory(name);
      DirectoryHelper.mkdir(dir);

      return "plocal:" + OFileUtils.getPath(dir.getAbsolutePath()).replace("//", "/");
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
