package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;

/**
 * Job created event.
 *
 * @since 3.1
 */
public class JobCreatedEvent
    extends EntityCreatedEvent
    implements JobEvent
{
  public JobCreatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public JobDetailEntity getJob() {
    return getEntity();
  }
}
