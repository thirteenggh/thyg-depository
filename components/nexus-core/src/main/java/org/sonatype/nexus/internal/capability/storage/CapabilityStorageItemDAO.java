package org.sonatype.nexus.internal.capability.storage;

import org.sonatype.nexus.datastore.api.IterableDataAccess;

/**
 * {@link CapabilityStorageItemData} access.
 *
 * @since 3.21
 */
public interface CapabilityStorageItemDAO
    extends IterableDataAccess<CapabilityStorageItemData>
{
  // no additional behaviour
}
