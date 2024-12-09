package org.sonatype.nexus.repository.routing.internal.orient;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.repository.routing.RoutingRuleInvalidatedEvent;

/**
 * @since 3.17
 */
public class OrientRoutingRuleDeletedEvent
    extends EntityDeletedEvent
    implements RoutingRuleInvalidatedEvent
{
  public OrientRoutingRuleDeletedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public EntityId getRoutingRuleId() {
    return getId();
  }
}
