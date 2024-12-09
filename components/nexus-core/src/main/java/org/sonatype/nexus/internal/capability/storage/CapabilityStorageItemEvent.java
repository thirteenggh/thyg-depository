package org.sonatype.nexus.internal.capability.storage;

import org.sonatype.nexus.capability.CapabilityIdentity;

/**
 * {@link CapabilityStorageItem} event.
 *
 * @since 3.1
 */
public interface CapabilityStorageItemEvent
{
  boolean isLocal();

  CapabilityIdentity getCapabilityId();

  CapabilityStorageItem getCapabilityStorageItem();
}
