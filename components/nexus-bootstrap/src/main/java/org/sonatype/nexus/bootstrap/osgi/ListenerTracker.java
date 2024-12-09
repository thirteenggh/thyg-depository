package org.sonatype.nexus.bootstrap.osgi;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Watches for a {@link ServletContextListener} with the given name and applies {@link ServletContext} events.
 * 
 * @since 3.0
 */
public final class ListenerTracker
    extends ServiceTracker<ServletContextListener, ServletContextListener>
{
  private static final String QUERY = "(&(objectClass=" + ServletContextListener.class.getName() + ")(name=%s))";

  private final ServletContext servletContext;

  public ListenerTracker(BundleContext bundleContext, String name, ServletContext servletContext)
      throws InvalidSyntaxException
  {
    super(bundleContext, FrameworkUtil.createFilter(String.format(QUERY, name)), null);
    this.servletContext = servletContext;
  }

  @Override
  public ServletContextListener addingService(ServiceReference<ServletContextListener> reference) {
    ServletContextListener service = super.addingService(reference);
    service.contextInitialized(new ServletContextEvent(servletContext));
    return service;
  }

  @Override
  public void removedService(ServiceReference<ServletContextListener> reference, ServletContextListener service) {
    service.contextDestroyed(new ServletContextEvent(servletContext));
    super.removedService(reference, service);
  }
}
