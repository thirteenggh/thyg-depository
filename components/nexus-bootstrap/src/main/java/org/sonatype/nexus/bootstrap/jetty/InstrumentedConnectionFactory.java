package org.sonatype.nexus.bootstrap.jetty;

import java.util.List;

import com.codahale.metrics.SharedMetricRegistries;
import org.eclipse.jetty.server.ConnectionFactory;

/**
 * Extension of {@link com.codahale.metrics.jetty9.InstrumentedConnectionFactory}.
 *
 * @since 3.0
 */
public final class InstrumentedConnectionFactory
    extends com.codahale.metrics.jetty9.InstrumentedConnectionFactory
{
  private final ConnectionFactory connectionFactory;

  public InstrumentedConnectionFactory(final ConnectionFactory connectionFactory) {
    super(connectionFactory, SharedMetricRegistries.getOrCreate("nexus").timer("connection-duration"));
    this.connectionFactory = connectionFactory;
  }

  // HACK: metrics-jetty9 (presently) is based on jetty 9.2, but we have to add more api for jetty 9.3

  @Override
  public List<String> getProtocols() {
    return connectionFactory.getProtocols();
  }
}
