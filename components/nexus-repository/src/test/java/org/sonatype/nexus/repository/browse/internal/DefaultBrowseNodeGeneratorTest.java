package org.sonatype.nexus.repository.browse.internal;

import java.util.List;

import org.sonatype.nexus.repository.browse.BrowseNodeGenerator;
import org.sonatype.nexus.repository.browse.BrowsePaths;
import org.sonatype.nexus.repository.browse.BrowseTestSupport;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DefaultBrowseNodeGeneratorTest
    extends BrowseTestSupport
{
  private BrowseNodeGenerator generator = new DefaultBrowseNodeGenerator();

  @Test
  public void computeAssetPathWithNoComponent() {
    Asset asset = createAsset("/path/asset");

    List<BrowsePaths> paths = generator.computeAssetPaths(asset, null);

    assertPaths(asList("path", "asset"), paths);
  }

  @Test
  public void computeAssetPathWithNoComponent_trailingSlash() {
    Asset asset = createAsset("/path/asset/");

    List<BrowsePaths> paths = generator.computeAssetPaths(asset, null);

    assertPaths(asList("path", "asset"), paths);
  }

  @Test
  public void computeAssetPathWithComponentNameOnly() {
    Component component = createComponent("component", null, null);
    Asset asset = createAsset("path/assetName");

    List<BrowsePaths> paths = generator.computeAssetPaths(asset, component);

    assertPaths(asList(component.name(), "assetName"), paths);
  }

  @Test
  public void computeAssetPathWithComponentNoGroup() {
    Component component = createComponent("component", null, "1.0.0");
    Asset asset = createAsset("path/assetName");

    List<BrowsePaths> paths = generator.computeAssetPaths(asset, component);

    assertPaths(asList(component.name(), component.version(), "assetName"), paths);
  }

  @Test
  public void computeAssetPathWithComponent() {
    Component component = createComponent("component", "group", "1.0.0");
    Asset asset = createAsset("path/assetName");

    List<BrowsePaths> paths = generator.computeAssetPaths(asset, component);

    assertPaths(asList(component.group(), component.name(), component.version(), "assetName"), paths);
  }

  @Test
  public void computeComponentPathWithComponentNameOnly() {
    Component component = createComponent("component", null, null);
    Asset asset = createAsset("path/assetName");

    List<BrowsePaths> paths = generator.computeComponentPaths(asset, component);

    assertPaths(singletonList(component.name()), paths, true);
  }

  @Test
  public void computeComponentPathWithComponentNoGroup() {
    Component component = createComponent("component", null, "1.0.0");
    Asset asset = createAsset("path/assetName");

    List<BrowsePaths> paths = generator.computeComponentPaths(asset, component);

    assertPaths(asList(component.name(), component.version()), paths, true);
  }

  @Test
  public void computeComponentPathWithComponent() {
    Component component = createComponent("component", "group", "1.0.0");
    Asset asset = createAsset("path/assetName");

    List<BrowsePaths> paths = generator.computeComponentPaths(asset, component);

    assertPaths(asList(component.group(), component.name(), component.version()), paths, true);
  }

  @Test
  public void lastSegmentBehaviour() {
    assertThat(generator.lastSegment(""), is(""));
    assertThat(generator.lastSegment("/"), is(""));
    assertThat(generator.lastSegment("//"), is(""));
    assertThat(generator.lastSegment("///"), is(""));
    assertThat(generator.lastSegment("////"), is(""));
    assertThat(generator.lastSegment("a"), is("a"));
    assertThat(generator.lastSegment("a/"), is("a"));
    assertThat(generator.lastSegment("/a"), is("a"));
    assertThat(generator.lastSegment("/a/"), is("a"));
    assertThat(generator.lastSegment("//a"), is("a"));
    assertThat(generator.lastSegment("a//"), is("a"));
    assertThat(generator.lastSegment("//a//"), is("a"));
    assertThat(generator.lastSegment("a/b"), is("b"));
    assertThat(generator.lastSegment("a/b/"), is("b"));
    assertThat(generator.lastSegment("/a/b"), is("b"));
    assertThat(generator.lastSegment("/a/b/"), is("b"));
    assertThat(generator.lastSegment("a/.b"), is(".b"));
    assertThat(generator.lastSegment("a/b.c"), is("b.c"));
  }
}
