package org.sonatype.nexus.repository.browse.internal;

import org.sonatype.nexus.repository.query.QueryOptions;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Builds SQL for sorting, limiting and setting the start record.
 *
 * @since 3.3
 */
public class SuffixSqlBuilder
{
  private final QueryOptions queryOptions;

  private SuffixSqlBuilder(final QueryOptions queryOptions) {
    this.queryOptions = checkNotNull(queryOptions);
  }

  public static String buildSuffix(final QueryOptions queryOptions) {
    return new SuffixSqlBuilder(queryOptions).build();
  }

  private String build() {
    return sort() + start() + limit();
  }

  private String sort() {
    String sortProperty = queryOptions.getSortProperty();
    String sortDirection = queryOptions.getSortDirection();
    if (sortProperty != null && sortDirection != null && "id".equals(sortProperty)) {
      return " ORDER BY @rid " + sortDirection;
    }
    return "";
  }

  private String start() {
    Integer start = queryOptions.getStart();
    if (start != null) {
      return " SKIP " + start;
    }
    return "";
  }

  private String limit() {
    Integer limit = queryOptions.getLimit();
    if (limit != null) {
      return " LIMIT " + limit;
    }
    return "";
  }
}
