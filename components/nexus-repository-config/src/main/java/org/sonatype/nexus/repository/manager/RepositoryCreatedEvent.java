package org.sonatype.nexus.repository.manager;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryEvent;

/**
 * Emitted when a repository has been created.
 *
 * @since 3.0
 */
public class RepositoryCreatedEvent
  extends RepositoryEvent
{
  public RepositoryCreatedEvent(final Repository repository) {
    super(repository);
  }
}
