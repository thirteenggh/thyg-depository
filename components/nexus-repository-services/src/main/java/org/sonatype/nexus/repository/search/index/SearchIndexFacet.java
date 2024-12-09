package org.sonatype.nexus.repository.search.index;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.Repository;

/**
 * Search index {@link Facet}.
 *
 * @since 3.25
 */
@Facet.Exposed
public interface SearchIndexFacet
    extends Facet
{
  /**
   * Rebuilds the index of the {@link Repository} this facet is attached to.
   */
  void rebuildIndex();
}
