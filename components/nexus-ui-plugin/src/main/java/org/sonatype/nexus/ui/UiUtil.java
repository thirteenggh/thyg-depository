package org.sonatype.nexus.ui;

import java.net.URL;
import java.util.Enumeration;

import org.eclipse.sisu.space.ClassSpace;

/**
 * @since 3.20
 */
public class UiUtil
{
  /**
   * @param filename
   * @param space
   * @return the path to the requested file
   */
  public static String getPathForFile(final String filename, final ClassSpace space) {
    for (
        Enumeration<URL> e = space.findEntries("static", filename, true);
        e.hasMoreElements();
    ) {
      URL url = e.nextElement();
      return url.getPath();
    }
    return null;
  }
}
