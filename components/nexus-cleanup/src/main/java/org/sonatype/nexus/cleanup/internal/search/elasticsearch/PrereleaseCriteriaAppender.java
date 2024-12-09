package org.sonatype.nexus.cleanup.internal.search.elasticsearch;

import javax.inject.Named;

import org.elasticsearch.index.query.BoolQueryBuilder;

import static java.lang.Boolean.parseBoolean;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.IS_PRERELEASE_KEY;

/**
 * Adds criteria for querying on isPrerelease
 * 
 * @since 3.14
 */
@Named(IS_PRERELEASE_KEY)
public class PrereleaseCriteriaAppender
  implements CriteriaAppender
{
  @Override
  public void append(final BoolQueryBuilder query, final String value) {
    query.must(matchQuery(IS_PRERELEASE_KEY, parseBoolean(value)));
  }
}
