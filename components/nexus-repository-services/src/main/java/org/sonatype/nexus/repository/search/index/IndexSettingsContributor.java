package org.sonatype.nexus.repository.search.index;

import java.net.URL;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Repository;

/**
 * Contributor to ES index settings.
 *
 * Index settings from all contributors are merged before index is created.
 *
 * @since 3.0
 */
public interface IndexSettingsContributor
{
  /**
   * Resource name of ElasticSearch mapping configuration.
   */
  String MAPPING_JSON = "elasticsearch-mapping.json";

  /**
   * Retrieves index settings for specific repository.
   *
   * @return ES index settings in json format or null if this contributor has no settings for repository
   */
  @Nullable
  URL getIndexSettings(Repository repository);
}
