package org.sonatype.nexus.repository.content.search;

import java.util.Map;

import org.sonatype.nexus.repository.content.fluent.FluentComponent;

/**
 * Extension point to contribute fields to {@link SearchDocumentProducer}.
 *
 * @since 3.25
 */
public interface SearchDocumentExtension
{
  /**
   * Retrieves additional fields to be indexed for the given component.
   *
   * @return additional field names and their values in Map format
   */
  Map<String, Object> getFields(FluentComponent component);
}
