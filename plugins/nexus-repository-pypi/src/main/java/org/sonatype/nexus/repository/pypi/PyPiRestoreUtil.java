package org.sonatype.nexus.repository.pypi;

import org.sonatype.nexus.repository.pypi.internal.PyPiFileUtils;
import org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simple util class to provide limited exposure to specific utility
 * methods within the internal package.
 *
 * @since 3.16
 */
public class PyPiRestoreUtil
{
  public static String extractVersionFromPath(final String path) {
    checkNotNull(path);
    String fileName = PyPiFileUtils.extractFilenameFromPath(path);
    if (fileName.length() > 0) {
      return PyPiFileUtils.extractVersionFromFilename(fileName);
    }
    return null;
  }

  public static boolean isIndex(final String path) {
    checkNotNull(path);
    return PyPiPathUtils.isIndexPath(path) || PyPiPathUtils.isRootIndexPath(path);
  }

  private PyPiRestoreUtil() {
    // no op
  }
}
