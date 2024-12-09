package com.sonatype.nexus.ssl.plugin.spi;

import org.sonatype.nexus.capability.CapabilityReference;

/**
 * Manages retrieve / update of TrustStore capabilities / type.
 *
 * @since ssl 1.0
 */
public interface CapabilityManager
{

  CapabilityReference get(String id);

  CapabilityReference enable(String id, boolean enabled)
      throws Exception;

}
