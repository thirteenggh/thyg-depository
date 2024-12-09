package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;

/**
 * Job deleted event.
 *
 * @since 3.1
 */
public class JobDeletedEvent
    extends EntityDeletedEvent
    implements JobEvent
{
  public JobDeletedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public JobDetailEntity getJob() {
    return getEntity();
  }
}
