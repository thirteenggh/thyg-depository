package org.sonatype.nexus.rapture.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.CapabilityBooterSupport;
import org.sonatype.nexus.capability.CapabilityRegistry;
import org.sonatype.nexus.rapture.internal.settings.SettingsCapabilityConfiguration;
import org.sonatype.nexus.rapture.internal.settings.SettingsCapabilityDescriptor;

/**
 * Automatically create Rapture capabilities.
 *
 * @since 3.0
 */
@Named
@Singleton
public class RaptureCapabilitiesBooter
    extends CapabilityBooterSupport
{
  @Override
  protected void boot(final CapabilityRegistry registry) throws Exception {
    maybeAddCapability(
        registry,
        SettingsCapabilityDescriptor.TYPE,
        true, // enabled
        null, // no notes
        new SettingsCapabilityConfiguration().asMap()
    );
  }
}
