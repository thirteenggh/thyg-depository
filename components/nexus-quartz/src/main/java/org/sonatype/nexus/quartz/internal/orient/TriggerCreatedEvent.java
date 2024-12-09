package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;

/**
 * Trigger created event.
 *
 * @since 3.1
 */
public class TriggerCreatedEvent
    extends EntityCreatedEvent
    implements TriggerEvent
{
  public TriggerCreatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public TriggerEntity getTrigger() {
    return getEntity();
  }
}
