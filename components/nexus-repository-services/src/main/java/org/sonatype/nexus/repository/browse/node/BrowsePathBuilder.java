package org.sonatype.nexus.repository.browse.node;

import java.util.ArrayList;
import java.util.List;

import static org.sonatype.nexus.repository.browse.node.BrowsePath.SLASH_CHAR;

/**
 * Static methods for building {@link BrowsePath}.
 *
 * @since 3.25
 */
public class BrowsePathBuilder
{
  private BrowsePathBuilder() {
    // static utility class
  }

  public static List<BrowsePath> fromPaths(List<String> paths, boolean trailingSlash) {
    List<BrowsePath> results = new ArrayList<>();

    StringBuilder requestPath = new StringBuilder().append(SLASH_CHAR);
    for (int i = 0; i < paths.size(); i++) {
      requestPath.append(paths.get(i));
      if (trailingSlash || i < paths.size() - 1) {
        requestPath.append(SLASH_CHAR);
      }
      results.add(new BrowsePath(paths.get(i), requestPath.toString()));
    }

    return results;
  }

  public static void appendPath(List<BrowsePath> browsePaths, String path) {
    browsePaths.add(new BrowsePath(path, browsePaths.get(browsePaths.size() - 1).getRequestPath() + path));
  }

  public static void appendPath(List<BrowsePath> browsePaths, String path, String requestPath) {
    browsePaths.add(new BrowsePath(path, requestPath));
  }
}
