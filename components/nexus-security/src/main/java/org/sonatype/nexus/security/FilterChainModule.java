package org.sonatype.nexus.security;

import com.google.common.base.Joiner;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Support module for configuring {@link FilterChain}s.
 *
 * @since 2.5
 */
public abstract class FilterChainModule
    extends AbstractModule
{
  protected void addFilterChain(final String pathPattern, final String filterExpression) {
    bind(FilterChain.class)
        .annotatedWith(Names.named(pathPattern))
        .toInstance(new FilterChain(pathPattern, filterExpression));
  }

  protected void addFilterChain(final String pathPattern, final String... filterExpression) {
    addFilterChain(pathPattern, Joiner.on(",").join(filterExpression));
  }
}
