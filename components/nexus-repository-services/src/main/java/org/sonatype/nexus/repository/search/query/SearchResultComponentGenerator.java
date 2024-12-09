package org.sonatype.nexus.repository.search.query;

import java.util.Set;

import org.elasticsearch.search.SearchHit;

/**
 *
 *
 * @since 3.14
 */
public interface SearchResultComponentGenerator
{
  SearchResultComponent from(final SearchHit hit, final Set<String> componentIdSet);
}
