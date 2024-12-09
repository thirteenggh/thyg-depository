package org.sonatype.nexus.capability.condition.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.capability.condition.NexusConditions;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of {@link NexusConditions}.
 *
 * @since capabilities 2.0
 */
@Named
@Singleton
public class NexusConditionsImpl
    implements NexusConditions
{
  private final NexusIsActiveCondition nexusIsActiveCondition;

  @Inject
  public NexusConditionsImpl(final NexusIsActiveCondition nexusIsActiveCondition) {
    this.nexusIsActiveCondition = checkNotNull(nexusIsActiveCondition);
  }

  @Override
  public Condition active() {
    return nexusIsActiveCondition;
  }
}
