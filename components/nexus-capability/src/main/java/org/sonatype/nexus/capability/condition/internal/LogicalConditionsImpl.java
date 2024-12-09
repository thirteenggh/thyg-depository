package org.sonatype.nexus.capability.condition.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.capability.condition.LogicalConditions;
import org.sonatype.nexus.common.event.EventManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of {@link LogicalConditions}.
 *
 * @since capabilities 2.0
 */
@Named
@Singleton
public class LogicalConditionsImpl
    implements LogicalConditions
{

  private final EventManager eventManager;

  @Inject
  public LogicalConditionsImpl(final EventManager eventManager) {
    this.eventManager = checkNotNull(eventManager);
  }

  @Override
  public Condition and(final Condition... conditions) {
    return new ConjunctionCondition(eventManager, conditions);
  }

  @Override
  public Condition or(final Condition... conditions) {
    return new DisjunctionCondition(eventManager, conditions);
  }

  @Override
  public Condition not(final Condition condition) {
    return new InversionCondition(eventManager, condition);
  }
}
