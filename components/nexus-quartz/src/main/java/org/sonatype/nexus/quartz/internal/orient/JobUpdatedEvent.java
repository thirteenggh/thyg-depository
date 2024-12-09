package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;

/**
 * Job updated event.
 *
 * @since 3.1
 */
public class JobUpdatedEvent
    extends EntityUpdatedEvent
    implements JobEvent
{
  public JobUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public JobDetailEntity getJob() {
    return getEntity();
  }
}
