package org.sonatype.nexus.testsuite.testsupport.dispatch;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

/**
 * Matches requests as long as they have a particular query string parameter.
 */
public class QueryParamMatcher
    implements RequestMatcher
{
  private final String paramName;

  private final String value;

  public QueryParamMatcher(final String paramName) {
    this(paramName, null);
  }

  public QueryParamMatcher(final String paramName, final String value) {
    this.paramName = paramName;
    this.value = value;
  }

  @Override
  public boolean matches(final HttpServletRequest request) throws Exception {
    final URI uri = new URI("http://placeholder?" + request.getQueryString());
    List<NameValuePair> params = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);

    for (NameValuePair param : params) {
      if (param.getName().equals(paramName)) {
        return value == null ? true : value.equals(param.getValue());
      }
    }
    return false;
  }
}
