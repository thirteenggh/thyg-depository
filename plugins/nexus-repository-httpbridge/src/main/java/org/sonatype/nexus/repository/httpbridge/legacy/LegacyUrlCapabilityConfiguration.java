package org.sonatype.nexus.repository.httpbridge.legacy;

import org.sonatype.nexus.capability.CapabilityConfigurationSupport;

/**
 * {@link LegacyUrlCapability} configuration.
 *
 * @since 3.7
 */
public class LegacyUrlCapabilityConfiguration
    extends CapabilityConfigurationSupport
{
  @Override
  public String toString() {
    return getClass().getSimpleName() + "{}";
  }
}
