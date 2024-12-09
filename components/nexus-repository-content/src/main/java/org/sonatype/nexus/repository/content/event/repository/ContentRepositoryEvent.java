package org.sonatype.nexus.repository.content.event.repository;

import org.sonatype.nexus.repository.content.ContentRepository;
import org.sonatype.nexus.repository.content.store.ContentStoreEvent;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.content.store.InternalIds.contentRepositoryId;

/**
 * Base {@link ContentRepository} event.
 *
 * @since 3.26
 */
public class ContentRepositoryEvent
    extends ContentStoreEvent
{
  private final ContentRepository contentRepository;

  protected ContentRepositoryEvent(final ContentRepository contentRepository) {
    super(contentRepositoryId(contentRepository));
    this.contentRepository = checkNotNull(contentRepository);
  }

  public ContentRepository getContentRepository() {
    return contentRepository;
  }

  @Override
  public String toString() {
    return "ContentRepositoryEvent{" +
        "contentRepository=" + contentRepository +
        "} " + super.toString();
  }
}
