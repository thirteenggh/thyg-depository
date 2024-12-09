package org.sonatype.nexus.internal.metrics;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.codahale.metrics.health.HealthCheckRegistry;

/**
 * Customized {@link com.codahale.metrics.servlets.HealthCheckServlet} to support injection.
 *
 * @see HealthCheckMediator
 * @since 3.0
 * @deprecated As of 3.29 this servlet has been superseded by org.sonatype.nexus.internal.status.StatusResource.check
 */
@Deprecated
@Singleton
public class HealthCheckServlet
    extends com.codahale.metrics.servlets.HealthCheckServlet
{
  @Inject
  public HealthCheckServlet(final HealthCheckRegistry registry) {
    super(registry);
  }
}
