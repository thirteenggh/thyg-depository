package org.sonatype.nexus.security;

import java.util.Collection;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;

import static com.google.common.net.HttpHeaders.SET_COOKIE;

/**
 * Cookie munging filter.
 *
 * @since 2.11.2
 */
@Named
@Singleton
public class CookieFilter
    extends AdviceFilter
{
  private static final String SECURE_FLAG = "; Secure";

  @Override
  protected boolean preHandle(final ServletRequest request, final ServletResponse response) throws Exception {
    filterCookies(request, response);
    return true;
  }

  @Override
  protected void postHandle(final ServletRequest request, final ServletResponse response) throws Exception {
    filterCookies(request, response);
  }

  /**
   * Perform filtering on cookie headers.
   *
   * If the request is secure, examine response for cookies and adds the Secure flag if not already present in the
   * cookie value.
   */
  protected void filterCookies(final ServletRequest request, final ServletResponse response) {
    if (request.isSecure() && response instanceof HttpServletResponse) {
      secureCookies((HttpServletResponse) response);
    }
  }

  private void secureCookies(HttpServletResponse response) {
    final Collection<String> cookies = response.getHeaders(SET_COOKIE);
    boolean mustAdd = false;
    for (final String cookie : cookies) {
      final String cookieVal = cookie.lastIndexOf(SECURE_FLAG) == -1 ? cookie + SECURE_FLAG : cookie;
      if (mustAdd) {
        response.addHeader(SET_COOKIE, cookieVal);
      }
      else {
        response.setHeader(SET_COOKIE, cookieVal);
      }
      mustAdd = true;
    }
  }
}
