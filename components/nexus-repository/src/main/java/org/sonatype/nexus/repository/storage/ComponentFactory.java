package org.sonatype.nexus.repository.storage;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory for creating {@link Component} instances.
 *
 * @since 3.8
 */
@Singleton
@Named
public class ComponentFactory
{
  private final Set<ComponentDecorator> componentDecorators;

  @Inject
  public ComponentFactory(final Set<ComponentDecorator> componentDecorators)
  {
    this.componentDecorators = checkNotNull(componentDecorators);
  }

  public Component createComponent() {
    Component component = new DefaultComponent();
    for (ComponentDecorator componentDecorator : componentDecorators) {
      component = componentDecorator.decorate(component);
    }
    return component;
  }
}
