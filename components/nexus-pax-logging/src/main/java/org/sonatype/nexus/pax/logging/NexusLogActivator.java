package org.sonatype.nexus.pax.logging;

import org.sonatype.nexus.logging.task.ProgressTaskLogger;

import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;

/**
 * Adds logging statement immediately after activating pax-logging.
 *
 * @since 3.13
 */
public class NexusLogActivator
    extends org.ops4j.pax.logging.logback.internal.Activator
{
  @Override
  public void start(final BundleContext bundleContext) throws Exception {
    super.start(bundleContext);
    LoggerFactory.getLogger(NexusLogActivator.class).info("start");
  }

  @Override
  public void stop(final BundleContext bundleContext) throws Exception {
    ProgressTaskLogger.shutdown();
    super.stop(bundleContext);
  }
}
