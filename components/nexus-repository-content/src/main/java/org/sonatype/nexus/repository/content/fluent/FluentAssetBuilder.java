package org.sonatype.nexus.repository.content.fluent;

import java.util.Optional;

import org.sonatype.nexus.repository.content.Component;

/**
 * Fluent API to create/find an asset; at this point we already know the asset path.
 *
 * @since 3.21
 */
public interface FluentAssetBuilder
{
  /**
   * Continue building the asset using the given kind.
   *
   * @since 3.24
   */
  FluentAssetBuilder kind(String kind);

  /**
   * Continue building the asset using the given owning component.
   */
  FluentAssetBuilder component(Component component);

  /**
   * Gets the full asset using the details built so far; if it doesn't exist then it is created.
   */
  FluentAsset getOrCreate();

  /**
   * Find if an asset exists using the details built so far.
   */
  Optional<FluentAsset> find();
}
