package org.sonatype.nexus.internal.capability;

import javax.inject.Named;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Capability module.
 *
 * @since 3.0
 */
@Named
public class CapabilityModule
    extends AbstractModule
{
  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().build(ActivationConditionHandlerFactory.class));
    install(new FactoryModuleBuilder().build(ValidityConditionHandlerFactory.class));
  }
}
