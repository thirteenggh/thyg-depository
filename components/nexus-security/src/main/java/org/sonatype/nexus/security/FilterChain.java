package org.sonatype.nexus.security;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A Shiro filter chain (mapping between a path pattern and a filter expression).
 *
 * @since 2.5
 */
public class FilterChain
{
  private final String pathPattern;

  private final String filterExpression;

  public FilterChain(final String pathPattern, final String filterExpression) {
    this.pathPattern = checkNotNull(pathPattern);
    this.filterExpression = checkNotNull(filterExpression);
  }

  public String getPathPattern() {
    return pathPattern;
  }

  public String getFilterExpression() {
    return filterExpression;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "pathPattern='" + pathPattern + '\'' +
        ", filterExpression='" + filterExpression + '\'' +
        '}';
  }
}
