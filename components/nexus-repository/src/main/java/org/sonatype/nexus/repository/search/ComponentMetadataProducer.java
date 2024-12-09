package org.sonatype.nexus.repository.search;

import java.util.Map;

import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

/**
 * Producer of metadata to be indexed in JSON format.
 *
 * @since 3.0
 */
public interface ComponentMetadataProducer
{

  /**
   * Retrieves data to be indexed for a component.
   *
   * @return data to be indexed in json format
   */
  String getMetadata(Component component, Iterable<Asset> assets, Map<String, Object> additional);

}
