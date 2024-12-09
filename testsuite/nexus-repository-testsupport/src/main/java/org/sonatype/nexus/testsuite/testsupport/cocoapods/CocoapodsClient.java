package org.sonatype.nexus.testsuite.testsupport.cocoapods;
import java.io.IOException;
import java.net.URI;

import org.sonatype.nexus.testsuite.testsupport.FormatClientSupport;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Cocoapods client.
 */
public class CocoapodsClient
    extends FormatClientSupport
{
  public CocoapodsClient(final CloseableHttpClient httpClient,
                   final HttpClientContext httpClientContext,
                   final URI repositoryBaseUri)
  {
    super(httpClient, httpClientContext, repositoryBaseUri);
  }

  public CloseableHttpResponse fetch(final String path) throws IOException {
    return execute(new HttpGet(resolve(path)));
  }
}
