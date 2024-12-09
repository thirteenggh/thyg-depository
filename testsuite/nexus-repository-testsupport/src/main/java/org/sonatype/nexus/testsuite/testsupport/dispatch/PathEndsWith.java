package org.sonatype.nexus.testsuite.testsupport.dispatch;

import javax.servlet.http.HttpServletRequest;

/**
 * Matches requests where the path contains a particular string.
 */
public class PathEndsWith
    implements RequestMatcher
{
  private final String substring;

  public PathEndsWith(final String substring) {
    this.substring = substring;
  }

  @Override
  public boolean matches(final HttpServletRequest request) throws Exception {
    return request.getPathInfo().endsWith(substring);
  }
}
