package org.sonatype.nexus.repository.apt.internal;

import java.util.List;

import org.sonatype.nexus.repository.browse.BrowsePaths;
import org.sonatype.nexus.repository.browse.BrowseTestSupport;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

import org.junit.Test;

import static java.util.Arrays.asList;

/**
 * @since 3.17
 */
public class AptBrowseNodeGeneratorTest
    extends BrowseTestSupport
{
  private AptBrowseNodeGenerator generator = new AptBrowseNodeGenerator();

  @Test
  public void computeComponentPath() {
    Component component = createComponent("nano", "amd64", "1.0.0");
    Asset asset = createAsset("path/assetName");

    List<BrowsePaths> paths = generator.computeComponentPaths(asset, component);
    assertPaths(asList("packages", "n", "nano", "1.0.0", "amd64", "nano"), paths, true);
  }
}
