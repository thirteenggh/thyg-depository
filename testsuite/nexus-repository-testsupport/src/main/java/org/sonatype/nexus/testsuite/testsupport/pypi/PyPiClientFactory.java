package org.sonatype.nexus.testsuite.testsupport.pypi;

import java.net.URI;

import org.sonatype.nexus.testsuite.testsupport.NexusClientFactory;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Factory for creation of {@link PyPiClient}s
 *
 * @since 3.13
 */
public class PyPiClientFactory
    extends NexusClientFactory<PyPiClient>
{
  @Override
  public PyPiClient createClient(final CloseableHttpClient httpClient,
                                 final HttpClientContext httpClientContext,
                                 final URI repositoryBaseUri)
  {
    return new PyPiClient(httpClient, httpClientContext, repositoryBaseUri, null);
  }
}
