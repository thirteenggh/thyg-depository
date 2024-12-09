package org.sonatype.nexus.repository.storage.internal;

import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.repository.storage.Bucket;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link Bucket} created event.
 *
 * @since 3.6.1
 */
public class BucketCreatedEvent
    extends EntityCreatedEvent
    implements BucketEvent
{
  private final String repositoryName;

  public BucketCreatedEvent(final EntityMetadata metadata, final String repositoryName) {
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
