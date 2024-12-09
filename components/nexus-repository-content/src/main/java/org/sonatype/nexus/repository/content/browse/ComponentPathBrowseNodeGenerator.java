package org.sonatype.nexus.repository.content.browse;

import java.util.List;

import org.sonatype.nexus.repository.browse.node.BrowsePath;
import org.sonatype.nexus.repository.content.Asset;

/**
 * Component-led layout that places components one level above their assets.
 *
 * @since 3.24
 */
public abstract class ComponentPathBrowseNodeGenerator
    extends AssetPathBrowseNodeGenerator
{
  @Override
  public List<BrowsePath> computeComponentPaths(final Asset asset) {
    List<BrowsePath> assetPaths = computeAssetPaths(asset);
    return assetPaths.subList(0, assetPaths.size() - 1);
  }
}
