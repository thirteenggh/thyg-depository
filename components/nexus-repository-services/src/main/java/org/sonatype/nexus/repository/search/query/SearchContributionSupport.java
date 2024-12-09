package org.sonatype.nexus.repository.search.query;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryparser.classic.QueryParserBase;
import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * Support for {@link SearchContribution} implementations.
 *
 * @since 3.15
 */
public class SearchContributionSupport
    implements SearchContribution
{

  @Override
  public void contribute(final BoolQueryBuilder query, final String type, final String value) {
    // do nothing
  }

  public String escape(String value) {
    if (null == value) {
      return null;
    }

    String escaped = QueryParserBase.escape(value);

    boolean shouldLeaveDoubleQuotesEscaped = StringUtils.countMatches(value, "\"") % 2 != 0;
    String escapedCharactersRegex = shouldLeaveDoubleQuotesEscaped ? "\\\\([?*])" : "\\\\([?*\"])";

    // unescape supported special characters
    return escaped.replaceAll(escapedCharactersRegex, "$1");
  }

}
