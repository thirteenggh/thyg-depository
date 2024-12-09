package org.sonatype.nexus.rapture.internal;

import javax.inject.Named;

import org.sonatype.nexus.rapture.internal.security.SessionAuthenticationFilter;
import org.sonatype.nexus.rapture.internal.security.SessionServlet;
import org.sonatype.nexus.security.CookieFilter;
import org.sonatype.nexus.security.FilterChainModule;
import org.sonatype.nexus.security.SecurityFilter;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;

import static org.sonatype.nexus.security.FilterProviderSupport.filterKey;

/**
 * Rapture Guice module.
 *
 * @since 3.0
 */
@Named
public class RaptureModule
    extends AbstractModule
{
  private static final String MOUNT_POINT = "/service/rapture";

  private static final String SESSION_MP = MOUNT_POINT + "/session";

  @Override
  protected void configure() {
    bind(filterKey(SessionAuthenticationFilter.NAME)).to(SessionAuthenticationFilter.class);

    install(new ServletModule()
    {
      @Override
      protected void configureServlets() {
        serve(SESSION_MP).with(SessionServlet.class);
        filter(SESSION_MP).through(SecurityFilter.class);
        filter(SESSION_MP).through(CookieFilter.class);
      }
    });

    install(new FilterChainModule()
    {
      @Override
      protected void configure() {
        addFilterChain(SESSION_MP, SessionAuthenticationFilter.NAME);
      }
    });
  }
}
