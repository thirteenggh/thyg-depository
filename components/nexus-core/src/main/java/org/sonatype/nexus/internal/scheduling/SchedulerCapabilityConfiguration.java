package org.sonatype.nexus.internal.scheduling;

import java.util.Map;

import org.sonatype.nexus.capability.CapabilityConfigurationSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link SchedulerCapability} configuration.
 *
 * @since 3.0
 */
public class SchedulerCapabilityConfiguration
    extends CapabilityConfigurationSupport
{
  public SchedulerCapabilityConfiguration(final Map<String, String> properties) {
    checkNotNull(properties);
    // No specific configuration
  }
}
