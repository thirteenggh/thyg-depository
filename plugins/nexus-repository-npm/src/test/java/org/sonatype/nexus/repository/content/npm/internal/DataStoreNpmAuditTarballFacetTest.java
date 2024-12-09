package org.sonatype.nexus.repository.content.npm.internal;

import java.util.Optional;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.AssetBlob;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.proxy.ProxyFacet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Collections.singletonMap;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;

public class DataStoreNpmAuditTarballFacetTest
    extends TestSupport
{
  @Mock
  private Repository repository;

  @Mock
  private Context context;

  @Mock
  private ProxyFacet proxyFacet;

  @Mock
  private Content content;

  @Mock
  private FluentAsset anAsset;

  @Mock
  private AssetBlob assetBlob;

  private DataStoreNpmAuditTarballFacet underTest = new DataStoreNpmAuditTarballFacet(1);

  @Before
  public void setup() throws Exception {
    when(repository.facet(ProxyFacet.class)).thenReturn(proxyFacet);
    when(proxyFacet.get(context)).thenReturn(content);
    AttributesMap contentAttributes = new AttributesMap();
    contentAttributes.set(Asset.class, anAsset);
    when(content.getAttributes()).thenReturn(contentAttributes);
  }

  @Test
  public void componentHashsumShouldBeEmpty() throws Exception {
    when(anAsset.blob()).thenReturn(empty());

    Optional<String> componentHashsum = underTest.getComponentHashsumForProxyRepo(repository, context);

    assertThat(componentHashsum, is(empty()));
  }

  @Test
  public void componentHashsumShouldReturnHashsum() throws Exception {
    String aHash = "abcd1234";
    when(anAsset.blob()).thenReturn(of(assetBlob));
    when(assetBlob.checksums()).thenReturn(singletonMap(SHA1.name(), aHash));

    Optional<String> componentHashsum = underTest.getComponentHashsumForProxyRepo(repository, context);

    assertThat(componentHashsum, is(of(aHash)));
  }
}
