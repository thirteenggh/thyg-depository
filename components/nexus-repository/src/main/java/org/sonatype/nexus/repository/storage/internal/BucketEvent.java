package org.sonatype.nexus.repository.storage.internal;

import org.sonatype.nexus.repository.storage.Bucket;

/**
 * {@link Bucket} event.
 *
 * @since 3.6.1
 */
public interface BucketEvent
{
  boolean isLocal();

  String getRepositoryName();

  Bucket getBucket();
}
