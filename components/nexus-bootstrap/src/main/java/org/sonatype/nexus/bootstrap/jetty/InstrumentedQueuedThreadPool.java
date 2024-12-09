package org.sonatype.nexus.bootstrap.jetty;

import com.codahale.metrics.SharedMetricRegistries;

/**
 * Extension of {@link com.codahale.metrics.jetty9.InstrumentedQueuedThreadPool} that restores the default constructor.
 * 
 * @since 3.0
 */
public final class InstrumentedQueuedThreadPool
    extends com.codahale.metrics.jetty9.InstrumentedQueuedThreadPool
{
  public InstrumentedQueuedThreadPool() {
    super(SharedMetricRegistries.getOrCreate("nexus"));
  }
}
