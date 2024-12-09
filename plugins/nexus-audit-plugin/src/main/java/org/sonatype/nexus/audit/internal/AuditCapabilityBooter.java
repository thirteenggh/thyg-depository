package org.sonatype.nexus.audit.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.CapabilityBooterSupport;
import org.sonatype.nexus.capability.CapabilityRegistry;

/**
 * Audit {@link CapabilityBooterSupport}.
 *
 * @since 3.1
 */
@Named
@Singleton
public class AuditCapabilityBooter
    extends CapabilityBooterSupport
{
  @Override
  protected void boot(final CapabilityRegistry registry) throws Exception {
    maybeAddCapability(registry, AuditCapability.TYPE, true, null, null);
  }
}
