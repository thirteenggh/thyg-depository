package org.sonatype.nexus.repository.maven.internal;

import java.util.HashMap;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.maven.internal.orient.MavenWritePolicySelector;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.config.WritePolicy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.repository.maven.internal.Attributes.AssetKind.OTHER;
import static org.sonatype.nexus.repository.maven.internal.Attributes.AssetKind.REPOSITORY_METADATA;
import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;
import static org.sonatype.nexus.repository.config.WritePolicy.ALLOW;
import static org.sonatype.nexus.repository.config.WritePolicy.ALLOW_ONCE;
import static org.sonatype.nexus.repository.config.WritePolicy.DENY;

public class MavenWritePolicySelectorTest
    extends TestSupport
{
  @Mock
  private Asset asset;

  private MavenWritePolicySelector underTest;

  private NestedAttributesMap nestedAttributesMap;

  @Before
  public void setUp() {
    nestedAttributesMap = new NestedAttributesMap("", new HashMap<>());
    underTest = new MavenWritePolicySelector();
  }

  @Test
  public void select_withWritePolicyAllow() {
    WritePolicy writePolicy = underTest.select(asset, ALLOW);
    assertEquals(writePolicy, ALLOW);
  }

  @Test
  public void select_withWritePolicyDeny() {
    WritePolicy writePolicy = underTest.select(asset, DENY);
    assertEquals(writePolicy, DENY);
  }

  @Test
  public void select_withWritePolicyAllowOnceAndMetadataFile() {
    whenAssetFormatAttributes(REPOSITORY_METADATA.name());

    WritePolicy writePolicy = underTest.select(asset, ALLOW_ONCE);
    assertEquals(writePolicy, ALLOW);
  }

  @Test
  public void select_withWritePolicyAllowOnceAndOtherFile() {
    whenAssetFormatAttributes(OTHER.name());

    WritePolicy writePolicy = underTest.select(asset, ALLOW_ONCE);
    assertEquals(writePolicy, ALLOW_ONCE);
  }

  private void whenAssetFormatAttributes(String assetKindName) {
    nestedAttributesMap.backing().put(P_ASSET_KIND, assetKindName);
    when(asset.formatAttributes()).thenReturn(nestedAttributesMap);
  }
}
