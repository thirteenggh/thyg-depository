package org.sonatype.nexus.repository.rest.api;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for creating {@link ComponentXO} instances.
 *
 * @since 3.8
 */
@Singleton
@Named
public class ComponentXOFactory
{
  private final Set<ComponentXODecorator> componentXODecorators;

  @Inject
  public ComponentXOFactory(final Set<ComponentXODecorator> componentXODecorators) {
    this.componentXODecorators = checkNotNull(componentXODecorators);
  }

  public ComponentXO createComponentXO() {
    ComponentXO componentXO = new DefaultComponentXO();
    for (ComponentXODecorator componentXODecorator : componentXODecorators) {
      componentXO = componentXODecorator.decorate(componentXO);
    }
    return componentXO;
  }
}
