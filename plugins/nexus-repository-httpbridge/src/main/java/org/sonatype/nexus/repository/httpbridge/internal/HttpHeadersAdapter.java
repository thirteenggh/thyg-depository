package org.sonatype.nexus.repository.httpbridge.internal;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.sonatype.nexus.repository.view.Headers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * HTTP {@link Headers} adapter.
 *
 * @since 3.0
 */
class HttpHeadersAdapter
    extends Headers
{
  public HttpHeadersAdapter(final HttpServletRequest request) {
    checkNotNull(request);
    Enumeration<String> names = request.getHeaderNames();
    while (names.hasMoreElements()) {
      String name = names.nextElement();
      Enumeration<String> values = request.getHeaders(name);
      while (values.hasMoreElements()) {
        set(name, values.nextElement());
      }
    }
  }
}
