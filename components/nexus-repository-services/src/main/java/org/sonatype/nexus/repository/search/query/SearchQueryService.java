package org.sonatype.nexus.repository.search.query;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;

/**
 * Search query service.
 *
 * @since 3.25
 */
public interface SearchQueryService
{
  /**
   * Browse component metadata matches.
   */
  Iterable<SearchHit> browse(QueryBuilder query);

  /**
   * Search component metadata (paged).
   */
  SearchResponse search(QueryBuilder query, int from, int size);

  /**
   * Search component metadata (aggregated).
   */
  SearchResponse search(QueryBuilder query, List<AggregationBuilder> aggregations);

  /**
   * Count component metadata.
   */
  long count(QueryBuilder query);
}
