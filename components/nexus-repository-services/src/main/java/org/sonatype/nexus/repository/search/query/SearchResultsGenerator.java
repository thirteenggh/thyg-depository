package org.sonatype.nexus.repository.search.query;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
/**
 * Generates search results consumable by the UI
 *
 * @since 3.14
 */
public interface SearchResultsGenerator
{
  List<SearchResultComponent> getSearchResultList(SearchResponse response);
}
