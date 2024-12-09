package org.sonatype.nexus.internal.capability.storage.orient;

import org.sonatype.nexus.capability.CapabilityIdentity;
import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.internal.capability.storage.CapabilityStorageItem;
import org.sonatype.nexus.internal.capability.storage.CapabilityStorageItemCreatedEvent;

/**
 * {@link CapabilityStorageItem} created event.
 *
 * @since 3.1
 */
public class OrientCapabilityStorageItemCreatedEvent
    extends EntityCreatedEvent
    implements CapabilityStorageItemCreatedEvent
{
  public OrientCapabilityStorageItemCreatedEvent(final EntityMetadata metadata) {
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
