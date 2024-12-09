package org.sonatype.nexus.bootstrap.osgi;

import org.sonatype.nexus.bootstrap.jetty.ConnectorConfiguration;
import org.sonatype.nexus.bootstrap.jetty.JettyServer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Watches for a {@link ConnectorConfiguration} services and registers them with {@link JettyServer}.
 *
 * @since 3.0
 */
public final class ConnectorConfigurationTracker
    extends ServiceTracker<ConnectorConfiguration, ConnectorConfiguration>
{
  private final JettyServer jettyServer;

  public ConnectorConfigurationTracker(BundleContext bundleContext, JettyServer jettyServer)
  {
    super(bundleContext, ConnectorConfiguration.class.getName(), null);
    this.jettyServer = jettyServer;
  }

  @Override
  public ConnectorConfiguration addingService(ServiceReference<ConnectorConfiguration> reference) {
    ConnectorConfiguration service = super.addingService(reference);
    jettyServer.addCustomConnector(service);
    return service;
  }

  @Override
  public void removedService(ServiceReference<ConnectorConfiguration> reference, ConnectorConfiguration service) {
    jettyServer.removeCustomConnector(service);
    super.removedService(reference, service);
  }
}
