package org.sonatype.nexus.siesta;

import javax.inject.Named;

import org.sonatype.nexus.security.FilterChainModule;
import org.sonatype.nexus.security.SecurityFilter;
import org.sonatype.nexus.security.anonymous.AnonymousFilter;
import org.sonatype.nexus.security.authc.AntiCsrfFilter;
import org.sonatype.nexus.security.authc.NexusAuthenticationFilter;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Siesta plugin module.
 *
 * @since 2.4
 */
@Named
public class SiestaModule
    extends AbstractModule
{
  public static final String MOUNT_POINT = "/service/rest";

  private static final Logger log = LoggerFactory.getLogger(SiestaModule.class);

  public static final String SKIP_MODULE_CONFIGURATION = SiestaModule.class.getName() + ".skip";

  @Override
  protected void configure() {
    // HACK: avoid configuration of this module in cases as it is not wanted. e.g. automatically discovered by Sisu
    if (!Boolean.getBoolean(SKIP_MODULE_CONFIGURATION)) {
      doConfigure();
    }
  }

  private void doConfigure() {
    install(new ResteasyModule());

    install(new ServletModule()
    {
      @Override
      protected void configureServlets() {
        log.debug("Mount point: {}", MOUNT_POINT);

        bind(SiestaServlet.class);
        serve(MOUNT_POINT + "/*").with(SiestaServlet.class, ImmutableMap.of(
            "resteasy.servlet.mapping.prefix", MOUNT_POINT
        ));
        filter(MOUNT_POINT + "/*").through(SecurityFilter.class);
      }
    });

    install(new FilterChainModule()
    {
      @Override
      protected void configure() {
        addFilterChain(MOUNT_POINT + "/**",
            NexusAuthenticationFilter.NAME,
            AnonymousFilter.NAME,
            AntiCsrfFilter.NAME);
      }
    });
  }
}
