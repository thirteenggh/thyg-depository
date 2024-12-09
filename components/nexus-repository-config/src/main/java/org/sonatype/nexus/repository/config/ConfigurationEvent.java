package org.sonatype.nexus.repository.config;

import org.sonatype.nexus.common.event.HasLocality;

/**
 * Repository {@link Configuration} event.
 *
 * @since 3.1
 */
public interface ConfigurationEvent
    extends HasLocality
{
  boolean isLocal();

  String getRepositoryName();

  Configuration getConfiguration();
}
