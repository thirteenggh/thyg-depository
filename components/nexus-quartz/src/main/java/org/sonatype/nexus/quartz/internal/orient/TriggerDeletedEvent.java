package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;

/**
 * Trigger deleted event.
 *
 * @since 3.1
 */
public class TriggerDeletedEvent
    extends EntityDeletedEvent
    implements TriggerEvent
{
  public TriggerDeletedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public TriggerEntity getTrigger() {
    return getEntity();
  }
}
