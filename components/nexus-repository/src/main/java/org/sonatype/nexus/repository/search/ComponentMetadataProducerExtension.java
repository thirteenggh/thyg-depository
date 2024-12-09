package org.sonatype.nexus.repository.search;

import java.util.Map;

import org.sonatype.nexus.repository.storage.Component;

/**
 * Extension point for {@link ComponentMetadataProducer} to add additional fields to the Elasticsearch index
 *
 * @since 3.8
 *
 */
public interface ComponentMetadataProducerExtension
{
  /**
   * Get any data for the ES {@link ComponentMetadataProducer} from the {@link Component}
   */
  Map<String, Object> getComponentMetadata(Component component);
}
