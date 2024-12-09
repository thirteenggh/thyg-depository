package org.sonatype.nexus.testsuite.testsupport.maven;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

import org.sonatype.nexus.testsuite.testsupport.FormatClientSupport;

import com.google.common.net.HttpHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Maven2 Client.
 */
public class Maven2Client
    extends FormatClientSupport
{
  public Maven2Client(final CloseableHttpClient httpClient,
                      final HttpClientContext httpClientContext,
                      final URI repositoryBaseUri)
  {
    super(httpClient, httpClientContext, repositoryBaseUri);
  }

  public HttpResponse getIfNewer(String path, Date date) throws IOException {
    final URI uri = resolve(path);
    final HttpGet get = new HttpGet(uri);
    get.addHeader(HttpHeaders.IF_MODIFIED_SINCE, DateUtils.formatDate(date));
    return execute(get);
  }

  public HttpResponse getIfNoneMatch(String path, String etag) throws IOException {
    final URI uri = resolve(path);
    final HttpGet get = new HttpGet(uri);
    get.addHeader(HttpHeaders.IF_NONE_MATCH, etag);
    return execute(get);
  }

  public HttpResponse put(String path, HttpEntity entity) throws IOException {
    final URI uri = resolve(path);
    final HttpPut put = new HttpPut(uri);
    put.setEntity(entity);
    return execute(put);
  }

  public HttpResponse putIfUmmodified(String path, Date date, HttpEntity entity) throws IOException {
    final URI uri = resolve(path);
    final HttpPut put = new HttpPut(uri);
    put.addHeader(HttpHeaders.IF_UNMODIFIED_SINCE, DateUtils.formatDate(date));
    put.setEntity(entity);
    return execute(put);
  }

  public HttpResponse putIfMatches(String path, String etag, HttpEntity entity) throws IOException {
    final URI uri = resolve(path);
    final HttpPut put = new HttpPut(uri);
    put.addHeader(HttpHeaders.IF_MATCH, etag);
    put.setEntity(entity);
    return execute(put);
  }

  public HttpResponse delete(String path) throws IOException {
    final URI uri = resolve(path);
    final HttpDelete delete = new HttpDelete(uri);
    return execute(delete);
  }
}
