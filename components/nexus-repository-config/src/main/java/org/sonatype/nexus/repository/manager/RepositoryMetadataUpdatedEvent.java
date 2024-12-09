package org.sonatype.nexus.repository.manager;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryEvent;

/**
 * Emitted when a repository's metadata attributes have been updated.
 *
 * @since 3.6.1
 */
public class RepositoryMetadataUpdatedEvent
    extends RepositoryEvent
{
  public RepositoryMetadataUpdatedEvent(final Repository repository) {
    super(repository);
  }
}
