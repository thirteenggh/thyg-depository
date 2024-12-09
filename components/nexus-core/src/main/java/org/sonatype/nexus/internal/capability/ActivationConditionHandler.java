package org.sonatype.nexus.internal.capability;

import javax.inject.Inject;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.capability.CapabilityContextAware;
import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.capability.ConditionEvent;
import org.sonatype.nexus.capability.condition.Conditions;
import org.sonatype.nexus.common.event.EventManager;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.inject.assistedinject.Assisted;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Handles capability activation by reacting capability activation condition being satisfied/unsatisfied.
 *
 * @since capabilities 2.0
 */
public class ActivationConditionHandler
    extends ComponentSupport
{

  private final EventManager eventManager;

  private final DefaultCapabilityReference reference;

  private final Conditions conditions;

  private Condition activationCondition;

  @Inject
  ActivationConditionHandler(final EventManager eventManager,
                             final Conditions conditions,
                             @Assisted final DefaultCapabilityReference reference)
  {
    this.eventManager = checkNotNull(eventManager);
    this.conditions = checkNotNull(conditions);
    this.reference = checkNotNull(reference);
  }

  boolean isConditionSatisfied() {
    return activationCondition != null && activationCondition.isSatisfied();
  }

  @AllowConcurrentEvents
  @Subscribe
  public void handle(final ConditionEvent.Satisfied event) {
    if (event.getCondition() == activationCondition) {
      reference.activate();
    }
  }

  @AllowConcurrentEvents
  @Subscribe
  public void handle(final ConditionEvent.Unsatisfied event) {
    if (event.getCondition() == activationCondition) {
      reference.passivate();
    }
  }

  ActivationConditionHandler bind() {
    if (activationCondition == null) {
      try {
        Condition capabilityActivationCondition = reference.capability().activationCondition();
        if (capabilityActivationCondition == null) {
          capabilityActivationCondition = conditions.always("Capability has no activation condition");
        }
        activationCondition = conditions.logical().and(
            capabilityActivationCondition,
            conditions.nexus().active(),
            conditions.capabilities().capabilityHasNoFailures(),
            conditions.capabilities().capabilityHasNoDuplicates()
        );
        if (activationCondition instanceof CapabilityContextAware) {
          ((CapabilityContextAware) activationCondition).setContext(reference.context());
        }
      }
      catch (Exception e) {
        activationCondition = conditions.never("Failed to determine activation condition");
        log.error(
            "Could not get activation condition from capability {} ({}). Considering it as non activatable",
            reference.capability(), reference.context().id(), e
        );
      }
      activationCondition.bind();
      eventManager.register(this);
    }
    return this;
  }

  ActivationConditionHandler release() {
    if (activationCondition != null) {
      eventManager.unregister(this);
      activationCondition.release();
      activationCondition = null;
    }
    return this;
  }

  @Override
  public String toString() {
    return String.format(
        "Watching '%s' condition to activate/passivate capability '%s (id=%s)'",
        activationCondition, reference.capability(), reference.context().id()
    );
  }

  public String explainWhyNotSatisfied() {
    return isConditionSatisfied() ? null : activationCondition.explainUnsatisfied();
  }

}
