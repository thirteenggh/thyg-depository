package org.sonatype.nexus.repository.search.query;

import javax.inject.Named;
import javax.inject.Singleton;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * "attributes.nuget.tag" {@link SearchContribution} (adds a match query for tag).
 *
 * @since 3.15
 */
@Named("attributes.nuget.tags")
@Singleton
public class NugetTagsSearchContribution
    extends SearchContributionSupport
{

  @Override
  public void contribute(final BoolQueryBuilder query, final String type, final String value) {
    if (value != null) {
      query.must(QueryBuilders.matchQuery(type, value));
    }
  }

}
