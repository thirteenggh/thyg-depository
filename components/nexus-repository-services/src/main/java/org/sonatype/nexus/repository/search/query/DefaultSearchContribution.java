package org.sonatype.nexus.repository.search.query;

import javax.inject.Named;
import javax.inject.Singleton;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * "default" {@link SearchContribution} (adds filter as an ES term filter).
 *
 * @since 3.15
 */
@Named(DefaultSearchContribution.NAME)
@Singleton
public class DefaultSearchContribution
    extends SearchContributionSupport
{
  public static final String NAME = "default";

  @Override
  public void contribute(final BoolQueryBuilder query, final String type, final String value) {
    if (value != null) {
      String escaped = escape(value);
      query.must(QueryBuilders.queryStringQuery(escaped).field(type).lowercaseExpandedTerms(false));
    }
  }

}
