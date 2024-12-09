package org.sonatype.nexus.extender.modules;

import org.sonatype.nexus.extender.modules.internal.CachedGaugeTypeListener;

import com.codahale.metrics.SharedMetricRegistries;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.palominolabs.metrics.guice.DefaultMetricNamer;
import com.palominolabs.metrics.guice.MetricsInstrumentationModule;
import com.palominolabs.metrics.guice.annotation.MethodAnnotationResolver;

/**
 * Provides instrumentation of methods annotated with metrics annotations.
 * 
 * @since 3.0
 */
public class InstrumentationModule
    extends AbstractModule
{
  @Override
  protected void configure() {
    install(MetricsInstrumentationModule.builder()
        .withMetricRegistry(SharedMetricRegistries.getOrCreate("nexus"))
        .build());

    bindListener(Matchers.any(), new CachedGaugeTypeListener(SharedMetricRegistries.getOrCreate("nexus"), new DefaultMetricNamer(), new MethodAnnotationResolver()));
  }
}
