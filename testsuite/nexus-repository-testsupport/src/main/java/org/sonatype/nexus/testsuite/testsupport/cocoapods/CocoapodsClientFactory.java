package org.sonatype.nexus.testsuite.testsupport.cocoapods;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import java.net.URI;

import org.sonatype.nexus.testsuite.testsupport.NexusClientFactory;

public class CocoapodsClientFactory
    extends NexusClientFactory<CocoapodsClient>
{
  @Override
  public CocoapodsClient createClient(final CloseableHttpClient httpClient,
                                final HttpClientContext httpClientContext,
                                final URI repositoryBaseUri)
  {
    return new CocoapodsClient(httpClient, httpClientContext, repositoryBaseUri);
  }
}
