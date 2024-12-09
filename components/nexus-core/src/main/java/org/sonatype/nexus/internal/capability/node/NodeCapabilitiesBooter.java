package org.sonatype.nexus.internal.capability.node;

import java.util.Collections;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.CapabilityBooterSupport;
import org.sonatype.nexus.capability.CapabilityRegistry;

/**
 * Node capability booter.
 *
 * @since 3.0
 */
@Named
@Singleton
public class NodeCapabilitiesBooter
    extends CapabilityBooterSupport
{
  @Override
  protected void boot(final CapabilityRegistry registry) throws Exception {
    maybeAddCapability(
        registry,
        IdentityCapabilityDescriptor.TYPE,
        true, // enabled
        null, // no notes
        Collections.<String, String>emptyMap()
    );
  }
}
