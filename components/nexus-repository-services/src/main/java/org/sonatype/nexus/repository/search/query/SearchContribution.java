package org.sonatype.nexus.repository.search.query;

import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * Contributor to search query/filter.
 *
 * @since 3.15
 */
public interface SearchContribution
{

  /**
   * Contribute to search query/filter.
   *
   * @param query to contribute to
   * @param type   type of filter
   * @param value  value of filter
   */
  void contribute(BoolQueryBuilder query, String type, String value);

}
