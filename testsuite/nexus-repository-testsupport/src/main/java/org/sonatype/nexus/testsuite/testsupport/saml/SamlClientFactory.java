package org.sonatype.nexus.testsuite.testsupport.saml;

import java.net.URI;

import org.sonatype.nexus.testsuite.testsupport.NexusClientFactory;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

public class SamlClientFactory
    extends NexusClientFactory<SamlClient>
{

  public SamlClient createClient(final CloseableHttpClient httpClient,
                                final HttpClientContext httpClientContext,
                                final URI repositoryBaseUri)
  {
    return new SamlClient(httpClient, httpClientContext, repositoryBaseUri);
  }
}
