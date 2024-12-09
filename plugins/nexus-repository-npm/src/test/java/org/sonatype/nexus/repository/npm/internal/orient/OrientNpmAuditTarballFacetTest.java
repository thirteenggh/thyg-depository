package org.sonatype.nexus.repository.npm.internal.orient;

import java.util.Optional;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.proxy.ProxyFacet;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;

import com.google.common.hash.HashCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonMap;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;
import static org.sonatype.nexus.repository.view.Content.CONTENT_HASH_CODES_MAP;

public class OrientNpmAuditTarballFacetTest
    extends TestSupport
{
  @Mock
  private Repository repository;

  @Mock
  private ProxyFacet proxyFacet;

  @Mock
  private Content content;

  @Mock
  private StorageFacet storageFacet;

  @Mock
  private StorageTx storageTx;

  @Mock
  private Context context;

  private AttributesMap attributesMap;

  private OrientNpmAuditTarballFacet underTest = new OrientNpmAuditTarballFacet(1);

  @Before
  public void setup() throws Exception {
    when(repository.facet(ProxyFacet.class)).thenReturn(proxyFacet);
    when(proxyFacet.get(context)).thenReturn(content);
    attributesMap = new AttributesMap();
    when(content.getAttributes()).thenReturn(attributesMap);
    when(repository.facet(StorageFacet.class)).thenReturn(storageFacet);
    when(storageFacet.txSupplier()).thenReturn(() -> storageTx);
  }

  @Test
  public void componentHashsumShouldBeEmpty() throws Exception {
    Optional<String> componentHashsum = underTest.getComponentHashsumForProxyRepo(repository, context);

    assertThat(componentHashsum, is(empty()));
  }

  @Test
  public void componentHashsumShouldReturnHashsum() throws Exception {
    HashCode aHashCode = SHA1.function().hashString("sha1", UTF_8);
    attributesMap.set(CONTENT_HASH_CODES_MAP, singletonMap(SHA1, aHashCode));

    Optional<String> componentHashsum = underTest.getComponentHashsumForProxyRepo(repository, context);

    assertThat(componentHashsum, is(of(aHashCode.toString())));
  }
}
