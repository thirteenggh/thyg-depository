package org.sonatype.nexus.repository.browse;

import java.util.List;

import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

import org.junit.Test;

import static java.util.Arrays.asList;

public class AssetPathBrowseNodeGeneratorTest
  extends BrowseTestSupport
{
  private BrowseNodeGenerator generator = new AssetPathBrowseNodeGenerator()
  {
  };

  @Test
  public void computeAssetPathsNoComponent() {
    Asset asset = createAsset("asset/path/foo");

    List<BrowsePaths> paths = generator.computeAssetPaths(asset, null);

    assertPaths(asList("asset", "path", "foo"), paths);
  }

  @Test
  public void computeAssetPathsWithComponent() {
    Component component = createComponent("component", "group", "1.0.0");
    Asset asset = createAsset("asset/path/foo");

    List<BrowsePaths> paths = generator.computeAssetPaths(asset, component);

    assertPaths(asList("asset", "path", "foo"), paths);
  }

  @Test
  public void computeComponentPathsWithComponent() {
    Component component = createComponent("component", "group", "1.0.0");
    Asset asset = createAsset("asset/path/foo");

    List<BrowsePaths> paths = generator.computeComponentPaths(asset, component);

    assertPaths(asList("asset", "path", "foo"), paths);
  }
}
