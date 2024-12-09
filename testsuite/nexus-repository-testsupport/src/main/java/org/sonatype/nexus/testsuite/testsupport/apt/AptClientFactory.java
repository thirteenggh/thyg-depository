package org.sonatype.nexus.testsuite.testsupport.apt;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import java.net.URI;

import org.sonatype.nexus.testsuite.testsupport.NexusClientFactory;

public class AptClientFactory
    extends NexusClientFactory<AptClient>
{
  @Override
  public AptClient createClient(final CloseableHttpClient httpClient,
                                final HttpClientContext httpClientContext,
                                final URI repositoryBaseUri)
  {
    return new AptClient(httpClient, httpClientContext, repositoryBaseUri);
  }
}
