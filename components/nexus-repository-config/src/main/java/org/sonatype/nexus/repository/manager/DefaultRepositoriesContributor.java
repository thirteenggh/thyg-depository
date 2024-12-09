package org.sonatype.nexus.repository.manager;

import java.util.List;

import org.sonatype.nexus.repository.config.Configuration;

/**
 * Provides configurations for repositories that should be provisioned by default on first system startup.
 * @since 3.0
 */
public interface DefaultRepositoriesContributor
{
  /**
   * Provides Configurations that should be be initially provisioned.
   */
  List<Configuration> getRepositoryConfigurations();
}
