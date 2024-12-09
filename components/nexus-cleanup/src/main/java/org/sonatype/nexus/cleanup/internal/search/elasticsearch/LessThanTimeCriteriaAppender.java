package org.sonatype.nexus.cleanup.internal.search.elasticsearch;

import org.elasticsearch.index.query.BoolQueryBuilder;

import static java.lang.String.format;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

/**
 * Appends criteria for less than a given time.
 * 
 * @since 3.14
 */
public abstract class LessThanTimeCriteriaAppender
  implements CriteriaAppender
{
  private static final String NOW_MINUS_SECONDS = "now-%ss";
  
  private final String field;

  public LessThanTimeCriteriaAppender(final String field) {
    this.field = field;
  }
  
  public void append(final BoolQueryBuilder query, final String value) {
    query.filter(
        rangeQuery(field)
            .lte(format(NOW_MINUS_SECONDS, value))
    );
  }
}
