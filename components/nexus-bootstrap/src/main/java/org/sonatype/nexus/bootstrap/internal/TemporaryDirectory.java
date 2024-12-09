package org.sonatype.nexus.bootstrap.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Helper to ensure temporary directory is sane.
 *
 * @since 2.8
 */
public class TemporaryDirectory
{
  private TemporaryDirectory() {
    // empty
  }

  // NOTE: Do not reset the system property, we can not ensure this value will be used

  public static File get() throws IOException {
    String location = System.getProperty("java.io.tmpdir", "tmp");
    File dir = new File(location).getCanonicalFile();
    DirectoryHelper.mkdir(dir.toPath());

    // ensure we can create temporary files in this directory
    Path file = Files.createTempFile("nexus-tmpcheck", ".tmp");
    Files.delete(file);
    return dir;
  }
}
