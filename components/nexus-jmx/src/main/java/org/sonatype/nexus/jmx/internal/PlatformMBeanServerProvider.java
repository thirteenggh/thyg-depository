package org.sonatype.nexus.jmx.internal;

import java.lang.management.ManagementFactory;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.management.MBeanServer;

/**
 * Provides the platform {@link MBeanServer}.
 *
 * @since 3.0
 */
@Named("platform")
@Singleton
public class PlatformMBeanServerProvider
  implements Provider<MBeanServer>
{
  @Override
  public MBeanServer get() {
    return ManagementFactory.getPlatformMBeanServer();
  }
}
