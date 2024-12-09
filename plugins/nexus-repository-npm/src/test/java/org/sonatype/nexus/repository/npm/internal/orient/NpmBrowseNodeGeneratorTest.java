package org.sonatype.nexus.repository.npm.internal.orient;

import java.util.List;

import org.sonatype.nexus.repository.browse.BrowseNodeGenerator;
import org.sonatype.nexus.repository.browse.BrowsePaths;
import org.sonatype.nexus.repository.browse.BrowseTestSupport;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.DefaultComponent;

import org.junit.Test;

import static java.util.Arrays.asList;

public class NpmBrowseNodeGeneratorTest
    extends BrowseTestSupport
{
  private BrowseNodeGenerator generator = new NpmBrowseNodeGenerator();

  @Test
  public void computeAssetPathScopedComponent() {
    Asset asset = createAsset("@types/jquery/-/jquery-1.0.0.tgz");
    Component component = new DefaultComponent();

    List<BrowsePaths> assetPaths = generator.computeAssetPaths(asset, component);

    assertPaths(asList("@types", "jquery", "jquery-1.0.0.tgz"), asList("@types", "jquery", "-/jquery-1.0.0.tgz"), assetPaths);
  }

  @Test
  public void computeAssetPathComponent() {
    Asset asset = createAsset("jquery/-/jquery-1.0.0.tgz");
    Component component = new DefaultComponent();

    List<BrowsePaths> assetPaths = generator.computeAssetPaths(asset, component);

    assertPaths(asList("jquery", "jquery-1.0.0.tgz"), asList("jquery", "-/jquery-1.0.0.tgz"), assetPaths);
  }

  @Test
  public void computeComponentPathReturnsAssetPath() {
    Asset asset = createAsset("jquery/-/jquery-1.0.0.tgz");
    Component component = new DefaultComponent();

    List<BrowsePaths> assetPaths = generator.computeComponentPaths(asset, component);

    assertPaths(asList("jquery", "jquery-1.0.0.tgz"), asList("jquery", "-/jquery-1.0.0.tgz"), assetPaths);
  }
}
