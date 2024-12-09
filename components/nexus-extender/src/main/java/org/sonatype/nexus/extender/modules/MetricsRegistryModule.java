package org.sonatype.nexus.extender.modules;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.inject.AbstractModule;

/**
 * Provides access to the shared metrics and healthcheck registries.
 * 
 * @since 3.0
 */
public class MetricsRegistryModule
    extends AbstractModule
{
  static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();

  @Override
  protected void configure() {
    bind(MetricRegistry.class).toInstance(SharedMetricRegistries.getOrCreate("nexus"));
    bind(HealthCheckRegistry.class).toInstance(HEALTH_CHECK_REGISTRY);
  }
}
