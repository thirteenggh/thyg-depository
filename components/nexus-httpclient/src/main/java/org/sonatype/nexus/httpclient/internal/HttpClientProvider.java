package org.sonatype.nexus.httpclient.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.sonatype.nexus.httpclient.HttpClientManager;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.eclipse.sisu.Typed;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link HttpClient} (and {@link CloseableHttpClient}) provider via {@link HttpClientManager}.
 *
 * @since 3.0
 */
@Named
@Typed({HttpClient.class, CloseableHttpClient.class})
public class HttpClientProvider
    implements Provider<CloseableHttpClient>
{
  private final HttpClientManager httpClientManager;

  @Inject
  public HttpClientProvider(final HttpClientManager httpClientManager) {
    this.httpClientManager = checkNotNull(httpClientManager);
  }

  @Override
  public CloseableHttpClient get() {
    return httpClientManager.create();
  }
}
