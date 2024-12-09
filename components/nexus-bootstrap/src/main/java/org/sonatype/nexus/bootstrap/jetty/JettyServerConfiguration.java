package org.sonatype.nexus.bootstrap.jetty;

import java.util.List;

/**
 * Jetty server default configuration.
 *
 * @since 3.0
 */
public class JettyServerConfiguration
{
  private final List<ConnectorConfiguration> defaultConnectors;

  public JettyServerConfiguration(final List<ConnectorConfiguration> defaultConnectors) {
    this.defaultConnectors = defaultConnectors;
  }

  public List<ConnectorConfiguration> defaultConnectors() {
    return defaultConnectors;
  }
}
