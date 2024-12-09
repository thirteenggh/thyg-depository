package org.sonatype.nexus.testsuite.testsupport.maven;

import java.net.URI;

import org.sonatype.nexus.testsuite.testsupport.NexusClientFactory;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Factory for creation of {@link Maven2Client}s
 *
 * @since 3.16
 */
public class MavenClientFactory
    extends NexusClientFactory<Maven2Client>
{
  @Override
  public Maven2Client createClient(final CloseableHttpClient httpClient,
                                   final HttpClientContext httpClientContext,
                                   final URI repositoryBaseUri)
  {
    return new Maven2Client(httpClient, httpClientContext, repositoryBaseUri);
  }
}
