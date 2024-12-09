package org.sonatype.nexus.repository.manager;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryEvent;

/**
 * Emitted when a repository has been restored.
 *
 * @since 3.0
 */
public class RepositoryRestoredEvent
  extends RepositoryEvent
{
  public RepositoryRestoredEvent(final Repository repository) {
    super(repository);
  }
}
