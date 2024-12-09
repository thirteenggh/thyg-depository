package org.sonatype.nexus.repository.browse;

import java.util.List;

import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

/**
 * Component-led layout that places components one level above their assets.
 *
 * @since 3.7
 */
public abstract class ComponentPathBrowseNodeGenerator
    extends AssetPathBrowseNodeGenerator
{
  @Override
  public List<BrowsePaths> computeComponentPaths(final Asset asset, final Component component) {
    List<BrowsePaths> assetPaths = computeAssetPaths(asset, component);
    return assetPaths.subList(0, assetPaths.size() - 1);
  }
}
