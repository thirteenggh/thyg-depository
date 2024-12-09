package org.sonatype.nexus.repository.apt.internal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.browse.BrowseNodeGenerator;
import org.sonatype.nexus.repository.browse.BrowsePaths;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

import com.google.common.base.Splitter;

/**
 * @since 3.17
 */
@Singleton
@Named(AptFormat.NAME)
public class AptBrowseNodeGenerator
    implements
    BrowseNodeGenerator
{
  private static final String PACKAGES_PATH = "packages";

  private static final String METADATA_PATH = "metadata";
  @Override
  public List<BrowsePaths> computeAssetPaths(final Asset asset, @Nullable final Component component) {
    if (component != null) {
      List<BrowsePaths> paths = computeComponentPaths(asset, component);
      BrowsePaths.appendPath(paths, asset.name());
      return paths;
    }

    List<String> pathParts = new ArrayList<>();
    String name = asset.name();
    if (name.endsWith(".deb") || name.endsWith(".udeb")) {
      pathParts.add(PACKAGES_PATH);
    }
    else if (!name.startsWith("snapshots")) {
      pathParts.add(METADATA_PATH);
    }

    pathParts.addAll(Splitter.on('/').omitEmptyStrings().splitToList(name));

    return BrowsePaths.fromPaths(pathParts, false);
  }

  @Override
  public List<BrowsePaths> computeComponentPaths(final Asset asset, final Component component) {
    List<String> pathParts = new ArrayList<>();
    pathParts.add(PACKAGES_PATH);
    pathParts.add(component.name().substring(0, 1).toLowerCase());
    pathParts.add(component.name());
    pathParts.add(component.version());
    pathParts.add(component.group());
    pathParts.add(component.name());
    return BrowsePaths.fromPaths(pathParts, true);
  }
}
