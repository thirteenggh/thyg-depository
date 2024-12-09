package org.sonatype.nexus.repository.manager;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryEvent;

/**
 * Emitted when a repository has been deleted.
 *
 * @since 3.0
 */
public class RepositoryDeletedEvent
  extends RepositoryEvent
{
  public RepositoryDeletedEvent(final Repository repository) {
    super(repository);
  }
}
