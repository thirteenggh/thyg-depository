package org.sonatype.nexus.repository.storage;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.EntityId;

/**
 * Store providing access to the buckets.
 *
 * @since 3.6
 */
public interface BucketStore
{
  /**
   * @param repositoryName
   * @return the bucket for the repository name
   */
  Bucket read(String repositoryName);

  /**
   * Retrieve a bucket by its id
   */
  @Nullable
  Bucket getById(EntityId bucketId);
}
