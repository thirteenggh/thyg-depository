package org.sonatype.nexus.internal.capability.storage.orient;

import org.sonatype.nexus.capability.CapabilityIdentity;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;
import org.sonatype.nexus.internal.capability.storage.CapabilityStorageItem;
import org.sonatype.nexus.internal.capability.storage.CapabilityStorageItemUpdatedEvent;

/**
 * {@link CapabilityStorageItem} updated event.
 *
 * @since 3.1
 */
public class OrientCapabilityStorageItemUpdatedEvent
    extends EntityUpdatedEvent
    implements CapabilityStorageItemUpdatedEvent
{
  public OrientCapabilityStorageItemUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public CapabilityIdentity getCapabilityId() {
    return OrientCapabilityStorageItem.identity(getId());
  }

  @Override
  public CapabilityStorageItem getCapabilityStorageItem() {
    return getEntity();
  }
}
