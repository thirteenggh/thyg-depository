package org.sonatype.nexus.testsuite.testsupport.npm;

import java.net.URI;

import org.sonatype.nexus.testsuite.testsupport.NexusClientFactory;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

public class NpmClientFactory
    extends NexusClientFactory<NpmClient>
{

  public NpmClient createClient(final CloseableHttpClient httpClient,
                                final HttpClientContext httpClientContext,
                                final URI repositoryBaseUri)
  {
    return new NpmClient(httpClient, httpClientContext, repositoryBaseUri);
  }
}
