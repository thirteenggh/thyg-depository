package org.sonatype.nexus.pax.logging;

import com.codahale.metrics.SharedMetricRegistries;

/**
 * Extension of {@link com.codahale.metrics.logback.InstrumentedAppender} that restores the default constructor.
 * 
 * @since 3.0
 */
public final class InstrumentedAppender
    extends com.codahale.metrics.logback.InstrumentedAppender
{
  public InstrumentedAppender() {
    super(SharedMetricRegistries.getOrCreate("nexus"));
  }
}
