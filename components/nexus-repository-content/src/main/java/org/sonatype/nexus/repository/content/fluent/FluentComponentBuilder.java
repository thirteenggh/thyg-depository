package org.sonatype.nexus.repository.content.fluent;

import java.util.Optional;

/**
 * Fluent API to create/find a component; at this point we already know the component name.
 *
 * @since 3.21
 */
public interface FluentComponentBuilder
{
  /**
   * Continue building the component using the given namespace.
   */
  FluentComponentBuilder namespace(String namespace);

  /**
   * Continue building the component using the given kind.
   *
   * @since 3.25
   */
  FluentComponentBuilder kind(String kind);

  /**
   * Set {@code kind} only if a value is present.
   *
   * @since 3.29
   */
  FluentComponentBuilder kind(Optional<String> optionalKind);

  /**
   * Continue building the component using the given version.
   */
  FluentComponentBuilder version(String version);

  /**
   * Gets the full component using the details built so far; if it doesn't exist then it is created.
   */
  FluentComponent getOrCreate();

  /**
   * Find if a component exists using the details built so far.
   */
  Optional<FluentComponent> find();
}
