package org.sonatype.nexus.testsuite.testsupport.p2;

import java.net.URI;

import org.sonatype.nexus.testsuite.testsupport.FormatClientSupport;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

public class P2Client
    extends FormatClientSupport
{
  public P2Client(
      final CloseableHttpClient httpClient,
      final HttpClientContext httpClientContext,
      final URI repositoryBaseUri)
  {
    super(httpClient, httpClientContext, repositoryBaseUri);
  }
}
