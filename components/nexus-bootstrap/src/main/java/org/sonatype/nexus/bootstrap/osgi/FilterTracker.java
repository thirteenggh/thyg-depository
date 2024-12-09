package org.sonatype.nexus.bootstrap.osgi;

import javax.servlet.Filter;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Watches for a {@link Filter} with the given name and registers it with {@link DelegatingFilter}.
 * 
 * @since 3.0
 */
public final class FilterTracker
    extends ServiceTracker<Filter, Filter>
{
  private static final String QUERY = "(&(objectClass=" + Filter.class.getName() + ")(name=%s))";

  public FilterTracker(BundleContext bundleContext, String name) throws InvalidSyntaxException {
    super(bundleContext, FrameworkUtil.createFilter(String.format(QUERY, name)), null);
  }

  @Override
  public Filter addingService(ServiceReference<Filter> reference) {
    Filter service = super.addingService(reference);
    DelegatingFilter.set(service);
    return service;
  }

  @Override
  public void removedService(ServiceReference<Filter> reference, Filter service) {
    DelegatingFilter.unset(service);
    super.removedService(reference, service);
  }
}
