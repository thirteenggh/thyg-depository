package org.sonatype.nexus.repository.content.store;

import org.sonatype.nexus.repository.Repository;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Base event emitted by content stores.
 *
 * @since 3.26
 */
public class ContentStoreEvent
{
  final int contentRepositoryId;

  private Repository repository;

  protected ContentStoreEvent(final int contentRepositoryId) {
    this.contentRepositoryId = contentRepositoryId;
  }

  public String getFormat() {
    return getRepository().getFormat().getValue();
  }

  public Repository getRepository() {
    checkState(this.repository != null, "Repository has not been set");
    return repository;
  }

  void setRepository(final Repository repository) {
    checkState(this.repository == null, "Repository is already set");
    this.repository = checkNotNull(repository);
  }

  @Override
  public String toString() {
    return "ContentStoreEvent{" +
        "contentRepositoryId=" + contentRepositoryId +
        ", repository=" + repository +
        '}';
  }
}
