package org.sonatype.nexus.testsuite.testsupport.dispatch;

import javax.servlet.http.HttpServletRequest;

/**
 * Matches incoming servlet requests
 */
public interface RequestMatcher
{
  boolean matches(HttpServletRequest request) throws Exception;
}
