package org.sonatype.nexus.repository.browse;

import java.util.List;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

/**
 * Defines the browse node layout for components and assets of the same format.
 *
 * @since 3.6
 */
public interface BrowseNodeGenerator
{
  List<BrowsePaths> computeAssetPaths(Asset asset, @Nullable Component component);

  List<BrowsePaths> computeComponentPaths(Asset asset, Component component);

  /**
   * @return last segment of the given path string
   *
   * @since 3.7
   */
  default String lastSegment(final String path) {
    int lastNonSlash = path.length() - 1;
    while (lastNonSlash >= 0 && path.charAt(lastNonSlash) == '/') {
      lastNonSlash--;
    }
    int precedingSlash = path.lastIndexOf('/', lastNonSlash - 1);
    return path.substring(precedingSlash + 1, lastNonSlash + 1);
  }
}
