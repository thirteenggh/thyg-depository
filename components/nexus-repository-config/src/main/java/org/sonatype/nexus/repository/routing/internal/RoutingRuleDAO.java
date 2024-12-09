package org.sonatype.nexus.repository.routing.internal;

import org.sonatype.nexus.datastore.api.IterableDataAccess;

/**
 * {@link RoutingRuleData} access.
 *
 * @since 3.21
 */
public interface RoutingRuleDAO
    extends IterableDataAccess.WithName<RoutingRuleData>
{
  // no additional behaviour
}
