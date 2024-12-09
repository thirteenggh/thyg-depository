package org.sonatype.nexus.internal.scheduling;

import javax.inject.Named;

import org.sonatype.nexus.capability.CapabilityBooterSupport;
import org.sonatype.nexus.capability.CapabilityRegistry;

import org.eclipse.sisu.EagerSingleton;

/**
 * Creates {@link SchedulerCapability}.
 *
 * @since 3.0
 */
@Named
@EagerSingleton
public class SchedulerCapabilityBooter
    extends CapabilityBooterSupport
{
  @Override
  protected void boot(final CapabilityRegistry registry) throws Exception {
    maybeAddCapability(registry, SchedulerCapabilityDescriptor.TYPE, true, null, null);
  }
}
