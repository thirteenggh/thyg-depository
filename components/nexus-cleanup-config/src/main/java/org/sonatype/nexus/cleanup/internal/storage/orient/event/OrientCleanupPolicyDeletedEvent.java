package org.sonatype.nexus.cleanup.internal.storage.orient.event;

import org.sonatype.nexus.cleanup.storage.CleanupPolicy;
import org.sonatype.nexus.cleanup.storage.event.CleanupPolicyDeletedEvent;
import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;

/**
 * Event to be sent when a cleanup policy item has been deleted
 *
 * @since 3.14
 */
public class OrientCleanupPolicyDeletedEvent
    extends EntityDeletedEvent
    implements CleanupPolicyDeletedEvent
{
  public OrientCleanupPolicyDeletedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public CleanupPolicy getCleanupPolicy() {
    return getEntity();
  }
}
