package org.sonatype.nexus.cleanup.internal.storage.orient.event;

import org.sonatype.nexus.cleanup.storage.CleanupPolicy;
import org.sonatype.nexus.cleanup.storage.event.CleanupPolicyEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;

/**
 * Event to be sent when a cleanup policy item has been updated
 *
 * @since 3.14
 */
public class OrientCleanupPolicyUpdatedEvent
    extends EntityUpdatedEvent
    implements CleanupPolicyEvent
{
  public OrientCleanupPolicyUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public CleanupPolicy getCleanupPolicy() {
    return getEntity();
  }
}
