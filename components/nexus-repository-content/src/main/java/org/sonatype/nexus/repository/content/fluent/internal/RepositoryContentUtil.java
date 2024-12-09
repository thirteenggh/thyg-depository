package org.sonatype.nexus.repository.content.fluent.internal;

import java.util.Set;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.ContentRepository;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.types.GroupType;

import static java.util.stream.Collectors.toSet;

/**
 * Utility methods used by both {@link FluentAssetsImpl} and {@link FluentComponentsImpl}
 *
 * @since 3.27
 */
final class RepositoryContentUtil
{
  private RepositoryContentUtil() {
  }

  static boolean isGroupRepository(final Repository repository) {
    return GroupType.NAME.equals(repository.getType().getValue());
  }

  static Set<Integer> getLeafRepositoryIds(final Repository repository) {
    return repository.facet(GroupFacet.class).leafMembers().stream()
        .map(leafRepository -> leafRepository.facet(ContentFacet.class))
        .map(ContentRepository::contentRepositoryId)
        .collect(toSet());
  }
}
