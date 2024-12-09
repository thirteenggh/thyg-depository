package org.sonatype.nexus.repository.content.search;

import java.util.Map;

import org.sonatype.nexus.repository.content.fluent.FluentComponent;

/**
 * Producer of search documents to be indexed by Elasticsearch.
 *
 * @since 3.25
 */
public interface SearchDocumentProducer
{
  /**
   * Retrieves the search document to be indexed for the given component.
   *
   * @return search document in JSON format
   */
  String getDocument(FluentComponent component, Map<String, Object> commonFields);
}
