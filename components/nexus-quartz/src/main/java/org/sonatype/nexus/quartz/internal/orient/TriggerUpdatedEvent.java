package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;

/**
 * Trigger updated event.
 *
 * @since 3.1
 */
public class TriggerUpdatedEvent
    extends EntityUpdatedEvent
    implements TriggerEvent
{
  public TriggerUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public TriggerEntity getTrigger() {
    return getEntity();
  }
}
