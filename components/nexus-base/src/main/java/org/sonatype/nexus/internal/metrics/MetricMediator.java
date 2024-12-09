package org.sonatype.nexus.internal.metrics;

import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import org.eclipse.sisu.BeanEntry;
import org.eclipse.sisu.Mediator;

/**
 * Manages {@link Metric} registrations via Sisu component mediation.
 *
 * @since 3.6
 */
@Named
public class MetricMediator
    extends ComponentSupport
    implements Mediator<Named, Metric, MetricRegistry>
{
  public void add(final BeanEntry<Named, Metric> entry, final MetricRegistry registry) throws Exception {
    log.debug("Registering: {}", entry);
    registry.register(entry.getKey().value(), entry.getValue());
  }

  public void remove(final BeanEntry<Named, Metric> entry, final MetricRegistry registry) throws Exception {
    log.debug("Un-registering: {}", entry);
    registry.remove(entry.getKey().value());
  }
}
