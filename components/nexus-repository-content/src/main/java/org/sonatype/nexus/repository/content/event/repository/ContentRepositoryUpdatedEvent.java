package org.sonatype.nexus.repository.content.event.repository;

import org.sonatype.nexus.repository.content.ContentRepository;

/**
 * Event sent whenever a {@link ContentRepository} is updated.
 *
 * @since 3.26
 */
public class ContentRepositoryUpdatedEvent
    extends ContentRepositoryEvent
{
  protected ContentRepositoryUpdatedEvent(final ContentRepository contentRepository) {
    super(contentRepository);
  }
}
