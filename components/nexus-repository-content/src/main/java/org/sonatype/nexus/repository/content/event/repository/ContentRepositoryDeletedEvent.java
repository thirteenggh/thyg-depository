package org.sonatype.nexus.repository.content.event.repository;

import org.sonatype.nexus.repository.content.ContentRepository;

/**
 * Event sent whenever a {@link ContentRepository} is deleted.
 *
 * @since 3.26
 */
public class ContentRepositoryDeletedEvent
    extends ContentRepositoryEvent
{
  public ContentRepositoryDeletedEvent(final ContentRepository contentRepository) {
    super(contentRepository);
  }
}
