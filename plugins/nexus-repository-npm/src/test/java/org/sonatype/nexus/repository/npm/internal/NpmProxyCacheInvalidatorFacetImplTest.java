package org.sonatype.nexus.repository.npm.internal;

import java.util.Collections;
import java.util.Optional;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.manager.RepositoryUpdatedEvent;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NpmProxyCacheInvalidatorFacetImplTest
    extends TestSupport
{
  @Mock
  private NpmProxyFacet npmProxyFacet;

  private NpmProxyCacheInvalidatorFacetImpl underTest = new NpmProxyCacheInvalidatorFacetImpl();

  @Test
  public void testOnUrlChange() {
    underTest
        .on(new RepositoryUpdatedEvent(mockRepository("http://example.org"), mockConfiguration("http://example.com")));

    verify(npmProxyFacet).invalidateProxyCaches();
  }

  @Test
  public void testOnNoUrlChange() {
    underTest
        .on(new RepositoryUpdatedEvent(mockRepository("http://example.org"), mockConfiguration("http://example.org")));

    verify(npmProxyFacet, never()).invalidateProxyCaches();
  }

  private Repository mockRepository(final String remoteUrl) {
    Repository repository = mock(Repository.class);
    Configuration configuration = mockConfiguration(remoteUrl);

    when(repository.getConfiguration()).thenReturn(configuration);

    Optional<NpmProxyFacet> facet = Optional.of(npmProxyFacet);
    when(repository.optionalFacet(NpmProxyFacet.class)).thenReturn(facet);

    return repository;
  }

  private Configuration mockConfiguration(final String remoteUrl) {
    Configuration configuration = mock(Configuration.class);
    when(configuration.getAttributes())
        .thenReturn(Collections.singletonMap("proxy", Collections.singletonMap("remoteUrl", remoteUrl)));
    return configuration;
  }
}
