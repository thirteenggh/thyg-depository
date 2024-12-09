package org.sonatype.nexus.repository.content.search;

import java.util.Collection;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.search.index.SearchIndexFacet;

/**
 * Search {@link Facet}, that indexes/de-indexes component metadata.
 *
 * @since 3.25
 */
@Facet.Exposed
public interface SearchFacet
    extends SearchIndexFacet
{
  /**
   * Adds the given components to the search index.
   *
   * @param componentIds the components to index
   */
  void index(Collection<EntityId> componentIds);

  /**
   * Removes the given components from the search index.
   *
   * @param componentIds the components to purge
   */
  void purge(Collection<EntityId> componentIds);
}
