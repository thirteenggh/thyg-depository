package org.sonatype.nexus.repository.content.fluent;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.content.Component;

/**
 * Fluent API for components.
 *
 * @since 3.21
 */
public interface FluentComponents
    extends FluentQuery<FluentComponent>
{
  /**
   * Start building a component, beginning with its name.
   */
  FluentComponentBuilder name(String name);

  /**
   * Interact with an existing component.
   */
  FluentComponent with(Component component);

  /**
   * Query components that have the given kind.
   *
   * @since 3.26
   */
  FluentQuery<FluentComponent> byKind(String kind);

  /**
   * Query components that match the given filter.
   * <p>
   * A filter parameter of {@code foo} should be referred to in the filter string as <code>#{filterParams.foo}</code>
   * <p>
   * <b>WARNING</b> the filter string is appended to the query and should only contain trusted content!
   *
   * @since 3.26
   */
  FluentQuery<FluentComponent> byFilter(String filter, Map<String, Object> filterParams);

  /**
   * List all namespaces of components in the repository.
   */
  Collection<String> namespaces();

  /**
   * List all names of components under the given namespace in the repository.
   */
  Collection<String> names(String namespace);

  /**
   * List all versions of components with the given namespace and name in the repository.
   */
  Collection<String> versions(String namespace, String name);

  /**
   * Find if a component exists that has the given external id.
   *
   * @since 3.26
   */
  Optional<FluentComponent> find(EntityId externalId);
}
