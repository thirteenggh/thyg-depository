package org.sonatype.nexus.repository.browse;

import java.util.List;

import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

import org.junit.Test;

import static java.util.Arrays.asList;

public class ComponentPathBrowseNodeGeneratorTest
  extends BrowseTestSupport
{
  private BrowseNodeGenerator generator = new ComponentPathBrowseNodeGenerator()
  {
  };

  @Test
  public void computeAssetPathNoComponent() {
    Asset asset = createAsset("asset/path/foo");

    List<BrowsePaths> paths = generator.computeAssetPaths(asset, null);

    assertPaths(asList("asset", "path", "foo"), paths);
  }

  @Test
  public void computeAssetPathWithComponent() {
    Component component = createComponent("component", "group", "1.0.0");
    Asset asset = createAsset("asset/path/foo");

    List<BrowsePaths> paths = generator.computeAssetPaths(asset, component);

    assertPaths(asList("asset", "path", "foo"), paths);
  }

  @Test
  public void computeComponentPathWithComponent() {
    Component component = createComponent("component", "group", "1.0.0");
    Asset asset = createAsset("asset/path/foo");

    List<BrowsePaths> paths = generator.computeComponentPaths(asset, component);

    assertPaths(asList("asset", "path"), paths, true);
  }
}
