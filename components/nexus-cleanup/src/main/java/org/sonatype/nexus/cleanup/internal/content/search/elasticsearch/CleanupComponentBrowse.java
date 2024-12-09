package org.sonatype.nexus.cleanup.internal.content.search.elasticsearch;

import org.sonatype.nexus.cleanup.storage.CleanupPolicy;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.extdirect.model.PagedResponse;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.query.QueryOptions;

/**
 * Finds components to be cleaned up.
 *
 * @since 3.14
 */
public interface CleanupComponentBrowse
{
  Iterable<EntityId> browse(CleanupPolicy policy, Repository repository);

  /**
   * Returns a paged response of components
   *
   * @param policy cleanup policy criteria
   * @param repository repository to browse
   * @param options specifies paging and sorting criteria
   * @return a page of {@link Component}
   */
  PagedResponse<Component> browseByPage(CleanupPolicy policy, Repository repository, QueryOptions options);
}
