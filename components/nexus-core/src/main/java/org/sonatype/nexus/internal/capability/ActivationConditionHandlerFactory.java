package org.sonatype.nexus.internal.capability;

/**
 * Factory of {@link ActivationConditionHandler}.
 *
 * @since capabilities 2.0
 */
public interface ActivationConditionHandlerFactory
{

  ActivationConditionHandler create(final DefaultCapabilityReference reference);

}
