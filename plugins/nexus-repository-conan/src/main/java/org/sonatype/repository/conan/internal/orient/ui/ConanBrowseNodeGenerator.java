package org.sonatype.repository.conan.internal.orient.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.browse.BrowsePaths;
import org.sonatype.nexus.repository.browse.ComponentPathBrowseNodeGenerator;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.repository.conan.internal.ConanFormat;
import org.sonatype.repository.conan.internal.orient.utils.ConanFacetUtils;

import com.google.common.collect.ImmutableList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.28
 */
@Singleton
@Named(ConanFormat.NAME)
public class ConanBrowseNodeGenerator
    extends ComponentPathBrowseNodeGenerator
{
  public ConanBrowseNodeGenerator() {
    super();
  }

  @Override
  public List<BrowsePaths> computeComponentPaths(final Asset asset, final Component component) {
    List<String> componentList = new ArrayList<>();
    componentList.add(component.group());
    componentList.add(component.name());
    componentList.add(component.version());
    return BrowsePaths.fromPaths(componentList, true);
  }

  @Override
  public List<BrowsePaths> computeAssetPaths(final Asset asset, final Component component) {
    checkNotNull(asset);

    if (component != null) {
      List<BrowsePaths> strings = computeComponentPaths(asset, component);
      strings.addAll(assetSegment(asset.name()));
      return strings;
    }
    return super.computeAssetPaths(asset, null);
  }

  private List<BrowsePaths> assetSegment(final String path) {
    String[] split = path.split(File.separator);
    int fileNameIndex = split.length - 1;
    int packageNameIndex;
    int packagesSegmentIndex;

    if (path.contains(ConanFacetUtils.PACKAGE_SNAPSHOT_IDENTIFIER)) {
      if (ConanFacetUtils.isPackageSnapshot(path)) {
        packageNameIndex = split.length - 1;
        packagesSegmentIndex = split.length - 2;
        return BrowsePaths
            .fromPaths(ImmutableList.of(split[packagesSegmentIndex], split[packageNameIndex]),
                false);
      }
      else {
        packageNameIndex = split.length - 2;
        packagesSegmentIndex = split.length - 3;
        return BrowsePaths
            .fromPaths(ImmutableList
                    .of(split[packagesSegmentIndex], split[packageNameIndex], split[fileNameIndex]),
                false);
      }
    }
    return BrowsePaths.fromPaths(ImmutableList.of(split[fileNameIndex]), false);
  }
}
