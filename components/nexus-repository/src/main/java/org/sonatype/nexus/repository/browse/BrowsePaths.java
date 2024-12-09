package org.sonatype.nexus.repository.browse;

import java.util.ArrayList;
import java.util.List;

import org.sonatype.nexus.repository.browse.node.BrowsePath;

/**
 * Provides compatibility between the new {@link BrowsePath} interface and existing CMA path generators.
 *
 * @since 3.18
 */
public class BrowsePaths
    extends BrowsePath
{
  public BrowsePaths(final String displayName, final String requestPath) {
    super(displayName, requestPath);
  }

  public static List<BrowsePaths> fromPaths(List<String> paths, boolean trailingSlash) {
    List<BrowsePaths> results = new ArrayList<>();

    StringBuilder requestPath = new StringBuilder();
    for (int i = 0 ; i < paths.size() ; i++) {
      requestPath.append(paths.get(i));
      if (trailingSlash || i < paths.size() - 1) {
        requestPath.append("/");
      }
      results.add(new BrowsePaths(paths.get(i), requestPath.toString()));
    }

    return results;
  }

  public static void appendPath(List<BrowsePaths> browsePaths, String path) {
    browsePaths.add(new BrowsePaths(path, browsePaths.get(browsePaths.size() - 1).getRequestPath() + path));
  }

  public static void appendPath(List<BrowsePaths> browsePaths, String path, String requestPath) {
    browsePaths.add(new BrowsePaths(path, requestPath));
  }
}
