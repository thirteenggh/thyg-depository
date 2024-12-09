package org.sonatype.nexus.repository.routing;

import org.sonatype.nexus.common.entity.EntityId;

/**
 * @since 3.21
 */
public interface RoutingRuleInvalidatedEvent
{
  EntityId getRoutingRuleId();
}
