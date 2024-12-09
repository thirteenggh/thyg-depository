package org.sonatype.nexus.repository.content.fluent;

import java.util.Collection;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Component;

/**
 * Fluent API for a particular component.
 *
 * @since 3.21
 */
public interface FluentComponent
    extends Component, FluentAttributes<FluentComponent>
{
  /**
   * The repository containing this component.
   *
   * @since 3.24
   */
  Repository repository();

  /**
   * Start building an asset for this component, beginning with its path.
   */
  FluentAssetBuilder asset(String path);

  /**
   * List the assets under this component; returns an immutable collection.
   */
  Collection<FluentAsset> assets();

  /**
   * Update this component to have the given kind.
   *
   * @since 3.25
   */
  FluentComponent kind(String kind);

  /**
   * Deletes this component.
   */
  boolean delete();
}
