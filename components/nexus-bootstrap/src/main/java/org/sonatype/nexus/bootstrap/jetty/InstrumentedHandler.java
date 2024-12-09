package org.sonatype.nexus.bootstrap.jetty;

import com.codahale.metrics.SharedMetricRegistries;
import org.eclipse.jetty.server.Handler;

/**
 * Extension of {@link com.codahale.metrics.jetty9.InstrumentedHandler} that restores the delegate constructor.
 * 
 * @since 3.0
 */
public final class InstrumentedHandler
    extends com.codahale.metrics.jetty9.InstrumentedHandler
{
  public InstrumentedHandler(Handler delegate) {
    super(SharedMetricRegistries.getOrCreate("nexus"));
    setHandler(delegate);
  }
}
