package org.sonatype.nexus.repository.manager;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryEvent;

/**
 * Emitted when a repository has been loaded.
 *
 * @since 3.0
 */
public class RepositoryLoadedEvent
  extends RepositoryEvent
{
  public RepositoryLoadedEvent(final Repository repository) {
    super(repository);
  }
}
