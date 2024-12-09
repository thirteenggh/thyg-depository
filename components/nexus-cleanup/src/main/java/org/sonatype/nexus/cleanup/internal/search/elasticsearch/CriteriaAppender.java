package org.sonatype.nexus.cleanup.internal.search.elasticsearch;

import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * @since 3.14
 */
public interface CriteriaAppender
{
  /**
   * Adds an individual criteria to an ElasticSearch query
   * 
   * @param query
   * @param value
   */
  void append(BoolQueryBuilder query, String value);
}
