package org.sonatype.nexus.repository.storage.internal;

import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.repository.storage.Bucket;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link Bucket} deleted event.
 *
 * @since 3.6.1
 */
public class BucketDeletedEvent
    extends EntityDeletedEvent
    implements BucketEvent
{
  private final String repositoryName;

  public BucketDeletedEvent(final EntityMetadata metadata, final String repositoryName) {
    super(metadata);
    this.repositoryName = checkNotNull(repositoryName);
  }

  @Override
  public String getRepositoryName() {
    return repositoryName;
  }

  @Override
  public Bucket getBucket() {
    return getEntity();
  }
}
