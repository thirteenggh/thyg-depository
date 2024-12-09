package org.sonatype.nexus.bootstrap.internal;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Helper to create directories.
 *
 * @since 3.0
 */
public class DirectoryHelper
{
  private DirectoryHelper() {
    // empty
  }

  public static void mkdir(final Path dir) throws IOException {
    try {
      Files.createDirectories(dir);
    }
    catch (FileAlreadyExistsException e) {
      // this happens when last element of path exists, but is a symlink.
      // A simple test with Files.isDirectory should be able to detect this
      // case as by default, it follows symlinks.
      if (!Files.isDirectory(dir)) {
        throw new IOException("Failed to create directory: " + dir, e);
      }
    }
  }
}
