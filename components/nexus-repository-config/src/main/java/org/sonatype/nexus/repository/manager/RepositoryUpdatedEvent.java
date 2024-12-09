package org.sonatype.nexus.repository.manager;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryEvent;
import org.sonatype.nexus.repository.config.Configuration;

/**
 * Emitted when a repository has been updated.
 *
 * @since 3.0
 */
public class RepositoryUpdatedEvent
  extends RepositoryEvent
{
  private final Configuration oldConfiguration;

  public RepositoryUpdatedEvent(final Repository repository, final Configuration oldConfiguration) {
    super(repository);
    this.oldConfiguration = oldConfiguration;
  }

  /**
   * The previous configuration of the Repository.
   *
   * @since 3.21
   */
  public Configuration getOldConfiguration() {
    return oldConfiguration;
  }
}
