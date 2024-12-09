package org.sonatype.nexus.repository.browse;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.orient.entity.EntityAdapter;
import org.sonatype.nexus.repository.query.QueryOptions;

import static org.sonatype.nexus.repository.browse.internal.SuffixSqlBuilder.buildSuffix;

/**
 * @since 3.7
 */
public abstract class BrowseSqlBuilderSupport
    extends ComponentSupport
{
  protected abstract EntityAdapter<?> getEntityAdapter();

  protected abstract String getBrowseIndex();

  protected StringBuilder buildBase(QueryOptions queryOptions) {
    StringBuilder queryBuilder = new StringBuilder("SELECT FROM ");

    if ("id".equals(queryOptions.getSortProperty())) {
      queryBuilder.append(getEntityAdapter().getTypeName());
    }
    else {
      queryBuilder.append("INDEXVALUES");
      if (queryOptions.getSortDirection() != null) {
        queryBuilder.append(queryOptions.getSortDirection());
      }
      queryBuilder.append(":").append(getBrowseIndex());
    }

    return queryBuilder;
  }

  protected String buildQuerySuffix(final QueryOptions queryOptions) {
    return buildSuffix(queryOptions);
  }
}
