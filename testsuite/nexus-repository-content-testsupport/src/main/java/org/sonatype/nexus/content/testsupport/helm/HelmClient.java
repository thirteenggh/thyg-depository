package org.sonatype.nexus.content.testsupport.helm;

import java.io.IOException;
import java.net.URI;

import org.sonatype.nexus.content.testsupport.FormatClientSupport;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * A simple test client for Helm repositories.
 */
public class HelmClient
    extends FormatClientSupport
{
  public HelmClient(final CloseableHttpClient httpClient,
                    final HttpClientContext httpClientContext,
                    final URI repositoryBaseUri)
  {
    super(httpClient, httpClientContext, repositoryBaseUri);
  }

  public HttpResponse fetch(final String path, String contentType) throws IOException {
    HttpGet request = new HttpGet(resolve(path));
    request.addHeader("Content-Type", contentType);
    return execute(request);
  }

  public HttpResponse put(String path, HttpEntity entity) throws IOException {
    final URI resolve = resolve(path);
    final HttpPut put = new HttpPut(resolve);
    put.setEntity(entity);
    return execute(put);
  }
}
