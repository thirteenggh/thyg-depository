package org.sonatype.nexus.repository.content.browse;

import java.util.List;

import org.sonatype.nexus.repository.browse.node.BrowsePath;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;

public class AssetPathBrowseNodeGeneratorTest
    extends BrowseTestSupport
{
  private AssetPathBrowseNodeGenerator generator;

  @Before
  public void setup() {
    generator = new AssetPathBrowseNodeGenerator()
    {
    };
  }

  @Test
  public void computeAssetPathsNoComponent() {
    Asset asset = createAsset("asset/path/foo");

    List<BrowsePath> paths = generator.computeAssetPaths(asset);

    assertPaths(asList("asset", "path", "foo"), paths);
  }

  @Test
  public void computeAssetPathsWithComponent() {
    Component component = createComponent("component", "group", "1.0.0");
    Asset asset = createAsset("asset/path/foo", component);

    List<BrowsePath> paths = generator.computeAssetPaths(asset);

    assertPaths(asList("asset", "path", "foo"), paths);
  }

  @Test
  public void computeComponentPathsWithComponent() {
    Component component = createComponent("component", "group", "1.0.0");
    Asset asset = createAsset("asset/path/foo", component);

    List<BrowsePath> paths = generator.computeComponentPaths(asset);

    assertPaths(asList("asset", "path", "foo"), paths);
  }
}
