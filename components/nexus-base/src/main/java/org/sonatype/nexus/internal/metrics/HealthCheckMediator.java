package org.sonatype.nexus.internal.metrics;

import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.eclipse.sisu.BeanEntry;
import org.eclipse.sisu.Mediator;

/**
 * Manages {@link HealthCheck} registrations via Sisu component mediation.
 *
 * @since 2.8
 */
@Named
public class HealthCheckMediator
    extends ComponentSupport
    implements Mediator<Named, HealthCheck, HealthCheckRegistry>
{
  public void add(final BeanEntry<Named, HealthCheck> entry, final HealthCheckRegistry registry) throws Exception {
    log.debug("Registering: {}", entry);
    registry.register(entry.getKey().value(), entry.getValue());
  }

  public void remove(final BeanEntry<Named, HealthCheck> entry, final HealthCheckRegistry registry) throws Exception {
    log.debug("Un-registering: {}", entry);
    registry.unregister(entry.getKey().value());
  }
}

