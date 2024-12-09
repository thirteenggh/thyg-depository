package org.sonatype.nexus.internal.web;

import javax.inject.Named;

import org.sonatype.nexus.internal.metrics.MetricsModule;
import org.sonatype.nexus.internal.orient.OrientModule;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.servlet.DynamicGuiceFilter;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;
import org.eclipse.sisu.inject.Sources;

import static org.sonatype.nexus.common.property.SystemPropertiesHelper.getBoolean;

/**
 * Web module.
 * 
 * @since 3.0
 */
@Named
public class WebModule
    extends AbstractModule
{
  @Override
  protected void configure() {
    bind(GuiceFilter.class).to(DynamicGuiceFilter.class);

    // our configuration needs to be first-most when calculating order (some fudge room for edge-cases)
    final Binder highPriorityBinder = binder().withSource(Sources.prioritize(0x70000000));

    highPriorityBinder.install(new ServletModule()
    {
      @Override
      protected void configureServlets() {
        bind(HeaderPatternFilter.class);
        bind(EnvironmentFilter.class);
        bind(ErrorPageFilter.class);

        filter("/*").through(HeaderPatternFilter.class);
        filter("/*").through(EnvironmentFilter.class);
        filter("/*").through(ErrorPageFilter.class);

        bind(ErrorPageServlet.class);

        serve("/error.html").with(ErrorPageServlet.class);
        serve("/throw.html").with(ThrowServlet.class);
      }
    });

    highPriorityBinder.install(new MetricsModule());

    if (getBoolean("nexus.orient.enabled", true)) {
      install(new OrientModule());
    }
  }
}
