package org.sonatype.nexus.repository.routing.internal.orient;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;
import org.sonatype.nexus.repository.routing.RoutingRuleInvalidatedEvent;

/**
 * @since 3.17
 */
public class OrientRoutingRuleUpdatedEvent
    extends EntityUpdatedEvent
    implements RoutingRuleInvalidatedEvent
{
  public OrientRoutingRuleUpdatedEvent(final EntityMetadata metadata) {
    super(metadata);
  }

  @Override
  public EntityId getRoutingRuleId() {
    return getId();
  }
}
