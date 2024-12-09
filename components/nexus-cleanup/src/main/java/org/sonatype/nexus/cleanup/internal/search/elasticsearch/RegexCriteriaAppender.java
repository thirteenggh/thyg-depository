package org.sonatype.nexus.cleanup.internal.search.elasticsearch;

import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;

import com.google.common.annotations.VisibleForTesting;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.RegexpQueryBuilder;

import static org.sonatype.nexus.cleanup.storage.config.RegexCriteriaValidator.validate;
import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.REGEX_KEY;

/**
 * Appends criteria for matches based on regular expression
 *
 * @since 3.19
 */
@Named(REGEX_KEY)
public class RegexCriteriaAppender
    extends ComponentSupport
    implements CriteriaAppender
{
  @VisibleForTesting
  public static final String DEFAULT_REGEX_MATCH_ON = "assets.name";

  @Override
  public void append(final BoolQueryBuilder query, final String expression) {
    query.must(new RegexpQueryBuilder(DEFAULT_REGEX_MATCH_ON, validate(expression)));
  }
}
