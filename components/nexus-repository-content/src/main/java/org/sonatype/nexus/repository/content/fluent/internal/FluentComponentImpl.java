package org.sonatype.nexus.repository.content.fluent.internal;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Objects;

import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.AttributeOperation;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.facet.ContentFacetSupport;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentAssetBuilder;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.store.ComponentData;
import org.sonatype.nexus.repository.content.store.WrappedContent;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Collections2.transform;

/**
 * {@link FluentComponent} implementation.
 *
 * @since 3.24
 */
public class FluentComponentImpl
    implements FluentComponent, WrappedContent<Component>
{
  private final ContentFacetSupport facet;

  private final Component component;

  public FluentComponentImpl(final ContentFacetSupport facet, final Component component) {
    this.facet = checkNotNull(facet);
    this.component = checkNotNull(component);
  }

  @Override
  public Repository repository() {
    return facet.repository();
  }

  @Override
  public String namespace() {
    return component.namespace();
  }

  @Override
  public String name() {
    return component.name();
  }

  @Override
  public String kind() {
    return component.kind();
  }

  @Override
  public String version() {
    return component.version();
  }

  @Override
  public NestedAttributesMap attributes() {
    return component.attributes();
  }

  @Override
  public OffsetDateTime created() {
    return component.created();
  }

  @Override
  public OffsetDateTime lastUpdated() {
    return component.lastUpdated();
  }

  @Override
  public FluentComponent attributes(final AttributeOperation change, final String key, final Object value) {
    facet.stores().componentStore.updateComponentAttributes(component, change, key, value);
    return this;
  }

  @Override
  public FluentAssetBuilder asset(final String path) {
    return new FluentAssetBuilderImpl(facet, facet.stores().assetStore, path).component(this);
  }

  @Override
  public Collection<FluentAsset> assets() {
    return transform(facet.stores().assetStore.browseComponentAssets(component),
        asset -> new FluentAssetImpl(facet, asset));
  }

  @Override
  public FluentComponent kind(final String kind) {
    if (!Objects.equals(kind, component.kind())) {
      ((ComponentData) component).setKind(kind);
      facet.stores().componentStore.updateComponentKind(component);
    }
    return this;
  }

  @Override
  public boolean delete() {
    return facet.stores().componentStore.deleteComponent(component);
  }

  @Override
  public Component unwrap() {
    return component;
  }
}
