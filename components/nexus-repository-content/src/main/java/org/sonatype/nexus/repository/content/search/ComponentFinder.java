package org.sonatype.nexus.repository.content.search;

import java.util.stream.Stream;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;

/**
 * Helper to find components that match a particular UI model.
 *
 * @since 3.26
 */
public interface ComponentFinder
{
  Stream<FluentComponent> findComponentsByModel(
      final Repository repository,
      final String searchComponentId,
      final String namespace,
      final String name,
      final String version);
}
