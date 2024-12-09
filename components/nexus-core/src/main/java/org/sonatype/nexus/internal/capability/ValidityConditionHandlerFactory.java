package org.sonatype.nexus.internal.capability;

/**
 * Factory of {@link ValidityConditionHandler}.
 *
 * @since capabilities 2.0
 */
public interface ValidityConditionHandlerFactory
{

  ValidityConditionHandler create(final DefaultCapabilityReference reference);

}
