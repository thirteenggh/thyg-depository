package org.sonatype.nexus.internal.capability.storage.orient;

import org.sonatype.nexus.capability.CapabilityIdentity;
import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.internal.capability.storage.CapabilityStorageItem;
import org.sonatype.nexus.internal.capability.storage.CapabilityStorageItemDeletedEvent;

/**
 * {@link CapabilityStorageItem} deleted event.
 *
 * @since 3.1
 */
public class OrientCapabilityStorageItemDeletedEvent
    extends EntityDeletedEvent
    implements CapabilityStorageItemDeletedEvent
{
  public OrientCapabilityStorageItemDeletedEvent(final EntityMetadata metadata) {
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
