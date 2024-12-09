package org.sonatype.nexus.repository.search;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.search.index.SearchIndexFacet;
import org.sonatype.nexus.transaction.UnitOfWork;

/**
 * Search {@link Facet}, that index/de-index component metadata.
 *
 * @since 3.0
 */
@Facet.Exposed
public interface SearchFacet
    extends SearchIndexFacet
{
  /**
   * Indexes the metadata of the given component, requires an active {@link UnitOfWork}.
   */
  void put(EntityId componentId);

  /**
   * Indexes the metadata of the given components, requires an active {@link UnitOfWork}.
   *
   * @since 3.4
   */
  void bulkPut(Iterable<EntityId> componentIds);

  /**
   * De-indexes the metadata of the given component.
   */
  void delete(EntityId componentId);

  /**
   * De-indexes the metadata of the given components.
   *
   * @since 3.4
   */
  void bulkDelete(Iterable<EntityId> componentIds);
}
