package org.sonatype.nexus.repository.browse.internal;

import java.util.List;
import java.util.Map;

import org.sonatype.nexus.repository.browse.BrowseSqlBuilderSupport;
import org.sonatype.nexus.repository.query.QueryOptions;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.MetadataNode;

/**
 * {@link BrowseSqlBuilderSupport} extension for classes that extend {@link MetadataNode} in order to provide additional
 * support for repositories/buckets (namely {@link Component} and {@link Asset})
 *
 * @since 3.4
 */
public abstract class BrowseMetadataNodeSqlBuilderSupport
    extends BrowseSqlBuilderSupport
{
  /**
   * Returns the SQL for performing the build query.
   */
  String buildBrowseSql(final List<String> bucketIds, final QueryOptions queryOptions) {
    if (bucketIds.isEmpty()) {
      return "";
    }

    StringBuilder queryBuilder = buildBase(queryOptions);

    queryBuilder.append(" WHERE ").append(buildWhereClause(bucketIds, queryOptions)).append(' ')
        .append(buildQuerySuffix(queryOptions));

    return queryBuilder.toString();
  }

  protected abstract String buildWhereClause(final List<String> bucketIds, final QueryOptions queryOptions);

  /**
   * Returns the SQL parameters for performing the browse query.
   */
  abstract Map<String, Object> buildSqlParams(final String repositoryName, final QueryOptions queryOptions);
}
