package org.sonatype.nexus.repository.content.store;

import org.sonatype.nexus.common.entity.ContinuationAware;
import org.sonatype.nexus.repository.content.Component;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link Component} data backed by the content data store.
 *
 * @since 3.20
 */
public class ComponentData
    extends AbstractRepositoryContent
    implements Component, ContinuationAware
{
  Integer componentId; // NOSONAR: internal id

  private String namespace;

  private String name;

  private String kind;

  private String version;

  // Component API

  @Override
  public String namespace() {
    return namespace;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public String kind() {
    return kind;
  }

  @Override
  public String version() {
    return version;
  }

  // MyBatis setters + validation

  /**
   * Sets the internal component id.
   */
  public void setComponentId(final int componentId) {
    this.componentId = componentId;
  }

  /**
   * Sets the component namespace.
   */
  public void setNamespace(final String namespace) {
    this.namespace = checkNotNull(namespace);
  }

  /**
   * Sets the component name.
   */
  public void setName(final String name) {
    this.name = checkNotNull(name);
  }

  /**
   * Sets the component kind.
   *
   * @since 3.25
   */
  public void setKind(final String kind) {
    this.kind = checkNotNull(kind);
  }

  /**
   * Sets the component version.
   */
  public void setVersion(final String version) {
    this.version = checkNotNull(version);
  }

  // ContinuationAware

  @Override
  public String nextContinuationToken() {
    return Integer.toString(componentId);
  }

  @Override
  public String toString() {
    return "ComponentData{" +
        "componentId=" + componentId +
        ", namespace='" + namespace + '\'' +
        ", name='" + name + '\'' +
        ", kind='" + kind + '\'' +
        ", version='" + version + '\'' +
        "} " + super.toString();
  }
}
