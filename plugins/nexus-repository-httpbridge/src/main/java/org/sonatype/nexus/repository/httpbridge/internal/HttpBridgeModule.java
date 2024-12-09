package org.sonatype.nexus.repository.httpbridge.internal;

import javax.inject.Named;

import org.sonatype.nexus.security.FilterChainModule;
import org.sonatype.nexus.security.SecurityFilter;
import org.sonatype.nexus.security.anonymous.AnonymousFilter;
import org.sonatype.nexus.security.authc.AntiCsrfFilter;
import org.sonatype.nexus.security.authc.NexusAuthenticationFilter;
import org.sonatype.nexus.security.authc.apikey.ApiKeyAuthenticationFilter;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;

/**
 * Repository HTTP bridge module.
 *
 * @since 3.0
 */
@Named
public class HttpBridgeModule
    extends AbstractModule
{
  public static final String MOUNT_POINT = "/repository";

  @Override
  protected void configure() {
    install(new ServletModule()
    {
      @Override
      protected void configureServlets() {
        bind(ViewServlet.class);
        serve(MOUNT_POINT + "/*").with(ViewServlet.class);
        bindViewFiltersFor(MOUNT_POINT + "/*");
      }

      /**
       * Helper to make sure view-related filters are bound in the correct order by servlet filter.
       */
      private void bindViewFiltersFor(final String urlPattern, final String... morePatterns) {
        bindViewFilters(filter(urlPattern, morePatterns));
      }

      private void bindViewFilters(FilterKeyBindingBuilder filter) {
        filter.through(ExhaustRequestFilter.class);
        filter.through(SecurityFilter.class);
      }
    });

    install(new FilterChainModule()
    {
      @Override
      protected void configure() {
        addFilterChain(MOUNT_POINT + "/**",
            NexusAuthenticationFilter.NAME,
            ApiKeyAuthenticationFilter.NAME,
            AnonymousFilter.NAME,
            AntiCsrfFilter.NAME);
      }
    });
  }
}
