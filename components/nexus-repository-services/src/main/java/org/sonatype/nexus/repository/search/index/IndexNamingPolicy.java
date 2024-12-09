package org.sonatype.nexus.repository.search.index;

import org.sonatype.nexus.repository.Repository;

/**
 * Policy for naming repository search indexes.
 *
 * @since 3.25
 */
public interface IndexNamingPolicy
{
  /**
   * Returns the index name for the given repository.
   */
  String indexName(Repository repository);
}
