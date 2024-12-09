package org.sonatype.nexus.internal.capability.storage;

import java.util.Map;

import org.sonatype.goodies.lifecycle.Lifecycle;
import org.sonatype.nexus.capability.CapabilityIdentity;

/**
 * Capability storage.
 */
public interface CapabilityStorage
    extends Lifecycle
{
  /**
   * Adds a capability
   *
   * @param item to be added
   */
  CapabilityIdentity add(CapabilityStorageItem item);

  // FIXME: update() and remove() return values are never used, probably should return void instead

  /**
   * Updates stored capability if exists.
   *
   * @param item to be updated
   * @return false if capability to be updated does not exist in storage, true otherwise
   */
  boolean update(CapabilityIdentity id, CapabilityStorageItem item);

  /**
   * Deletes stored capability if exists.
   *
   * @param id of capability to be deleted
   * @return false if capability to be deleted does not exist in storage, true otherwise
   */
  boolean remove(CapabilityIdentity id);

  /**
   * Retrieves stored capabilities.
   *
   * @return capabilities (never null)
   */
  Map<CapabilityIdentity, CapabilityStorageItem> getAll();

  /**
   * @since 3.20
   */
  CapabilityStorageItem newStorageItem(final int version,
                                       final String type,
                                       final boolean enabled,
                                       final String notes,
                                       final Map<String, String> properties);
}
