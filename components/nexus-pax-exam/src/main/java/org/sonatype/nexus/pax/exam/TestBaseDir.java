package org.sonatype.nexus.pax.exam;

import java.io.File;
import java.nio.file.Paths;

/**
 * Helper to access files from the surrounding Maven project.
 * 
 * @since 3.2
 */
public class TestBaseDir
{
  private static final String BASEDIR = new File(System.getProperty("basedir", "")).getAbsolutePath();

  private TestBaseDir() {
    // hidden
  }

  /**
   * Gets the absolute path to the base directory of the surrounding Maven project.
   */
  public static String get() {
    return BASEDIR;
  }

  /**
   * Resolves path against the base directory of the surrounding Maven project.
   */
  public static File resolve(final String path) {
    return Paths.get(BASEDIR, path).toFile();
  }
}
