package org.sonatype.nexus.capability.condition.internal;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.Lifecycle;
import org.sonatype.nexus.capability.condition.ConditionSupport;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.event.EventManager;

import com.google.common.annotations.VisibleForTesting;

import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.CAPABILITIES;

/**
 * A condition that is satisfied when nexus is active.
 *
 * @since capabilities 2.0
 */
@Named
@ManagedLifecycle(phase = CAPABILITIES)
@Priority(Integer.MIN_VALUE) // nexus is considered active at the end of this phase
@Singleton
public class NexusIsActiveCondition
    extends ConditionSupport
    implements Lifecycle
{
  @VisibleForTesting
  public NexusIsActiveCondition(final EventManager eventManager) {
    super(eventManager, false);
    bind();
  }

  @Inject
  public NexusIsActiveCondition(final Provider<EventManager> eventManager) {
    super(eventManager, false);
    bind();
  }

  @Override
  public void start() {
    setSatisfied(true);
  }

  @Override
  public void stop() {
    setSatisfied(false);
  }

  @Override
  protected void doBind() {
    // do nothing
  }

  @Override
  protected void doRelease() {
    // do nothing
  }

  @Override
  public String toString() {
    return "Trust Repository is active";
  }

  @Override
  public String explainSatisfied() {
    return "Trust Repository is active";
  }

  @Override
  public String explainUnsatisfied() {
    return "Trust Repository is not active";
  }
}
