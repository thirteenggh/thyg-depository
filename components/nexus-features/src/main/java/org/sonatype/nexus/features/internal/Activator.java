package org.sonatype.nexus.features.internal;

import org.apache.karaf.features.FeaturesService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import static java.lang.System.getProperty;

/**
 * Installs the fast {@link FeaturesService} wrapper.
 *
 * @since 3.19
 */
public class Activator
    implements BundleActivator
{
  private FeaturesWrapper wrapper;

  @Override
  public void start(final BundleContext context) throws Exception {
    // set this property to true in system.properties to turn this feature off
    if (!"true".equalsIgnoreCase(getProperty("karaf.disableFastFeatures"))) {
      wrapper = new FeaturesWrapper(context);
      wrapper.open();
    }
  }

  @Override
  public void stop(final BundleContext context) throws Exception {
    if (wrapper != null) {
      wrapper.close();
      wrapper = null;
    }
  }
}
