package org.sonatype.nexus.repository.content.fluent.internal;

import java.util.Optional;

import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.fluent.FluentComponentBuilder;
import org.sonatype.nexus.repository.content.store.ComponentData;
import org.sonatype.nexus.repository.content.store.ComponentStore;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link FluentComponentBuilder} implementation.
 *
 * @since 3.24
 */
public class FluentComponentBuilderImpl
    implements FluentComponentBuilder
{
  private final ContentFacetSupport facet;

  private final ComponentStore<?> componentStore;

  private final String name;

  private String kind = "";

  private String namespace = "";

  private String version = "";

  public FluentComponentBuilderImpl(final ContentFacetSupport facet,
                                    final ComponentStore<?> componentStore,
                                    final String name)
  {
    this.facet = checkNotNull(facet);
    this.componentStore = checkNotNull(componentStore);
    this.name = checkNotNull(name);
  }

  @Override
  public FluentComponentBuilder namespace(final String namespace) {
    this.namespace = checkNotNull(namespace);
    return this;
  }

  @Override
  public FluentComponentBuilder kind(final String kind) {
    this.kind = checkNotNull(kind);
    return this;
  }

  @Override
  public FluentComponentBuilder kind(final Optional<String> optionalKind) {
    optionalKind.ifPresent(k -> this.kind = k);
    return this;
  }

  @Override
  public FluentComponentBuilder version(final String version) {
    this.version = checkNotNull(version);
    return this;
  }

  @Override
  public FluentComponent getOrCreate() {
    return new FluentComponentImpl(facet, componentStore.getOrCreate(this::findComponent, this::createComponent));
  }

  @Override
  public Optional<FluentComponent> find() {
    return findComponent().map(component -> new FluentComponentImpl(facet, component));
  }

  private Optional<Component> findComponent() {
    return componentStore.readCoordinate(facet.contentRepositoryId(), namespace, name, version);
  }

  private Component createComponent() {
    ComponentData component = new ComponentData();
    component.setRepositoryId(facet.contentRepositoryId());
    component.setNamespace(namespace);
    component.setName(name);
    component.setKind(kind);
    component.setVersion(version);

    componentStore.createComponent(component);

    return component;
  }
}
