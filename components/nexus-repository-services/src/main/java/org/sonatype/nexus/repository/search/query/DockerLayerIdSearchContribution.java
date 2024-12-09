package org.sonatype.nexus.repository.search.query;

import javax.inject.Named;
import javax.inject.Singleton;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * "attributes.docker.layerAncestry" {@link SearchContribution} (adds a prefix query for ancestry).
 *
 * @since 3.15
 */
@Named("attributes.docker.layerAncestry")
@Singleton
public class DockerLayerIdSearchContribution
    extends SearchContributionSupport
{

  @Override
  public void contribute(final BoolQueryBuilder query, final String type, final String value) {
    if (value != null) {
      query.must(QueryBuilders.prefixQuery(type, value));
    }
  }

}
