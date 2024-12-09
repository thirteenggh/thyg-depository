package org.sonatype.nexus.repository.httpclient;

import java.io.IOException;

import com.google.common.annotations.VisibleForTesting;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Support for wrapping {@link CloseableHttpClient}s.
 *
 * @since 3.1
 */
public abstract class FilteredHttpClientSupport
    extends CloseableHttpClient
{
  private final CloseableHttpClient delegate;

  public FilteredHttpClientSupport(final CloseableHttpClient delegate) {
    this.delegate = checkNotNull(delegate);
  }

  @Override
  public HttpParams getParams() {
    return delegate.getParams();
  }

  @Override
  public ClientConnectionManager getConnectionManager() {
    return delegate.getConnectionManager();
  }

  @Override
  protected CloseableHttpResponse doExecute(final HttpHost target, final HttpRequest request, final HttpContext context)
      throws IOException
  {
    return filter(target, () -> delegate.execute(target, request, context));
  }

  @Override
  public void close() throws IOException {
    delegate.close();
  }

  protected abstract CloseableHttpResponse filter(final HttpHost target, final Filterable filterable)
      throws IOException;

  @VisibleForTesting
  public interface Filterable
  {
    CloseableHttpResponse call() throws IOException;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "delegate=" + delegate +
        '}';
  }
}
