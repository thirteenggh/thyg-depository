package com.google.inject.servlet;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link GuiceFilter} that supports a dynamic ordered pipeline of filters and servlets.
 */
@Singleton
public final class DynamicGuiceFilter
    extends GuiceFilter
{
  @Inject
  DynamicGuiceFilter(DynamicFilterPipeline pipeline) {
    super(pipeline);
  }

  /**
   * ServletContextModule uses this to avoid "Multiple Servlet injectors detected" log spam.
   * 
   * @see nexus-extender
   */
  public static void avoidLogSpam() {
    GuiceFilter.pipeline = null;
  }
}
