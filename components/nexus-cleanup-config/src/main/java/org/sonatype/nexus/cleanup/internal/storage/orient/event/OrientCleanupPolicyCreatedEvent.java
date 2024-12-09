package org.sonatype.nexus.cleanup.internal.storage.orient.event;

import org.sonatype.nexus.cleanup.storage.CleanupPolicy;
import org.sonatype.nexus.cleanup.storage.event.CleanupPolicyEvent;
import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;

/**
 * Event to be sent when a cleanup policy item has been created
 *
 * @since 3.14
 */
public class OrientCleanupPolicyCreatedEvent
    extends EntityCreatedEvent
    implements CleanupPolicyEvent
{
  public OrientCleanupPolicyCreatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public CleanupPolicy getCleanupPolicy() {
    return getEntity();
  }
}
