package org.sonatype.nexus.testsuite.testsupport.conda;

import java.net.URI;

import org.sonatype.nexus.testsuite.testsupport.NexusClientFactory;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @since 3.19
 */
public class CondaClientFactory
    extends NexusClientFactory<CondaClient>
{
  @Override
  public CondaClient createClient(final CloseableHttpClient httpClient,
                                  final HttpClientContext httpClientContext,
                                  final URI repositoryBaseUri)
  {
    return new CondaClient(httpClient, httpClientContext, repositoryBaseUri);
  }
}
