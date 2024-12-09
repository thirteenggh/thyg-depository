package org.sonatype.nexus.testsuite.testsupport.dispatch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * A chain of {@link RequestMatcher}, matching iff all matchers do.
 */
public class ChainedRequestMatcher
    implements RequestMatcher
{
  private List<RequestMatcher> requestMatchers = new ArrayList<>();

  public static ChainedRequestMatcher forOperation(final String operation) {
    final ChainedRequestMatcher chainedRequestMatcher = new ChainedRequestMatcher();
    return chainedRequestMatcher.operation(operation);
  }

  public ChainedRequestMatcher operation(final String operation) {
    requestMatchers.add(new PathEndsWith(operation));
    return this;
  }

  public ChainedRequestMatcher hasParam(final String paramName) {
    requestMatchers.add(new QueryParamMatcher(paramName));
    return this;
  }

  public ChainedRequestMatcher hasParam(final String paramName, final String value) {
    requestMatchers.add(new QueryParamMatcher(paramName, value));
    return this;
  }

  @Override
  public boolean matches(final HttpServletRequest request) throws Exception {
    for (RequestMatcher matcher : requestMatchers) {
      if (!matcher.matches(request)) {
        return false;
      }
    }
    return true;
  }
}
