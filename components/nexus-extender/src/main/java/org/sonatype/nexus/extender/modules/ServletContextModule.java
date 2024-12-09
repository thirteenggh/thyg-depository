package org.sonatype.nexus.extender.modules;

import javax.servlet.ServletContext;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.DynamicGuiceFilter;

/**
 * Override guice-servlet's legacy {@link ServletContext} binding as it doesn't work well with multiple injectors.
 * 
 * @since 3.0
 */
public class ServletContextModule
    extends AbstractModule
{
  private final ServletContext servletContext;

  public ServletContextModule(final ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  @Override
  protected void configure() {
    bind(ServletContext.class).toInstance(servletContext);
    DynamicGuiceFilter.avoidLogSpam();
  }
}
