package org.sonatype.nexus.repository.r.internal;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.browse.BrowsePaths;
import org.sonatype.nexus.repository.browse.ComponentPathBrowseNodeGenerator;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * R places components at the same level as their assets.
 *
 * @since 3.28
 */
@Singleton
@Named(RFormat.NAME)
public class RBrowseNodeGenerator
    extends ComponentPathBrowseNodeGenerator
{
  @Override
  public List<BrowsePaths> computeAssetPaths(final Asset asset, @Nullable final Component component)
  {
    checkNotNull(asset);
    if (component != null) {
      List<BrowsePaths> paths = computeComponentPaths(asset, component);
      String lastSegment = lastSegment(asset.name());
      BrowsePaths.appendPath(paths, lastSegment);
      return paths;
    }
    else {
      return super.computeAssetPaths(asset, null);
    }
  }

  @Override
  public List<BrowsePaths> computeComponentPaths(final Asset asset, final Component component)
  {
    checkNotNull(asset);
    checkNotNull(component);
    List<BrowsePaths> browsePaths = super.computeComponentPaths(asset, null);
    if (!browsePaths.get(browsePaths.size() - 1).getDisplayName().equals(component.name())) {
      BrowsePaths.appendPath(browsePaths, component.name(), computeRequestPath(browsePaths, component.name()));
    }
    BrowsePaths.appendPath(browsePaths, component.version(), computeRequestPath(browsePaths, component.version()));
    return browsePaths;
  }

  private String computeRequestPath(List<BrowsePaths> browsePaths, String path) {
    return browsePaths.get(browsePaths.size() - 1).getRequestPath() + path + "/";
  }
}
