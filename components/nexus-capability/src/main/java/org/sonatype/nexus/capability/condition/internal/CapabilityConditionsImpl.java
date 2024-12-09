package org.sonatype.nexus.capability.condition.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.CapabilityDescriptorRegistry;
import org.sonatype.nexus.capability.CapabilityRegistry;
import org.sonatype.nexus.capability.CapabilityType;
import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.capability.Evaluable;
import org.sonatype.nexus.capability.condition.CapabilityConditions;
import org.sonatype.nexus.common.event.EventManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of {@link CapabilityConditions}.
 *
 * @since capabilities 2.0
 */
@Named
@Singleton
public class CapabilityConditionsImpl
    implements CapabilityConditions
{

  private final CapabilityRegistry capabilityRegistry;

  private final EventManager eventManager;

  private final CapabilityDescriptorRegistry descriptorRegistry;

  @Inject
  public CapabilityConditionsImpl(final EventManager eventManager,
                                  final CapabilityDescriptorRegistry descriptorRegistry,
                                  final CapabilityRegistry capabilityRegistry)
  {
    this.descriptorRegistry = checkNotNull(descriptorRegistry);
    this.capabilityRegistry = checkNotNull(capabilityRegistry);
    this.eventManager = checkNotNull(eventManager);
  }

  @Override
  public Condition capabilityOfTypeExists(final CapabilityType type) {
    return new CapabilityOfTypeExistsCondition(eventManager, descriptorRegistry, capabilityRegistry, type);
  }

  @Override
  public Condition capabilityOfTypeActive(final CapabilityType type) {
    return new CapabilityOfTypeActiveCondition(eventManager, descriptorRegistry, capabilityRegistry, type);
  }

  @Override
  public Condition passivateCapabilityDuringUpdate() {
    return new PassivateCapabilityDuringUpdateCondition(eventManager);
  }

  @Override
  public Condition passivateCapabilityWhenPropertyChanged(final String... propertyNames) {
    return new PassivateCapabilityDuringUpdateCondition(eventManager, propertyNames);
  }

  @Override
  public Condition capabilityHasNoFailures() {
    return new CapabilityHasNoFailuresCondition(eventManager);
  }

  @Override
  public Condition evaluable(final Evaluable condition) {
    return new EvaluableCondition(eventManager, condition);
  }

  @Override
  public Condition capabilityHasNoDuplicates() {
    return new CapabilityHasNoDuplicatesCondition(eventManager);
  }
}
