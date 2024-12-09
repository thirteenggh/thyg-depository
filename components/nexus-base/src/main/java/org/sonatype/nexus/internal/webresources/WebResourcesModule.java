package org.sonatype.nexus.internal.webresources;

import javax.inject.Named;

import org.sonatype.nexus.security.FilterChainModule;
import org.sonatype.nexus.security.SecurityFilter;
import org.sonatype.nexus.security.anonymous.AnonymousFilter;
import org.sonatype.nexus.security.authc.AntiCsrfFilter;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.servlet.ServletModule;
import org.eclipse.sisu.inject.Sources;

/**
 * Web resources module. Both servlet and filter-chain are installed with the lowest priority.
 *
 * @since 2.8
 */
@Named
public class WebResourcesModule
    extends AbstractModule
{
  @Override
  protected void configure() {
    final Binder lowPriorityBinder = binder().withSource(Sources.prioritize(Integer.MIN_VALUE));

    lowPriorityBinder.install(new ServletModule()
    {
      @Override
      protected void configureServlets() {
        serve("/*").with(WebResourceServlet.class);
        filter("/*").through(SecurityFilter.class);
      }
    });

    lowPriorityBinder.install(new FilterChainModule()
    {
      @Override
      protected void configure() {
        addFilterChain("/**", AnonymousFilter.NAME, AntiCsrfFilter.NAME);
      }
    });
  }
}
