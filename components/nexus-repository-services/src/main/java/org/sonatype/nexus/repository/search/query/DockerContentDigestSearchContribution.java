package org.sonatype.nexus.repository.search.query;

import javax.inject.Named;
import javax.inject.Singleton;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * "attributes.docker.content_digest" {@link SearchContribution} (adds a prefix query for content_digest).
 *
 * @since 3.15
 */
@Named("assets.attributes.docker.content_digest")
@Singleton
public class DockerContentDigestSearchContribution
    extends SearchContributionSupport
{

  @Override
  public void contribute(final BoolQueryBuilder query, final String type, final String value) {
    if (value != null) {
      query.filter(QueryBuilders.termQuery(type, value));
    }
  }

}
