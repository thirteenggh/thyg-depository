package org.sonatype.nexus.blobstore.group;

import org.sonatype.nexus.blobstore.api.BlobStore;

/**
 * Blob store group service.
 *
 * @since 3.15
 */
public interface BlobStoreGroupService {
  /**
   * Are blob store groups enabled, i.e. can they be created and modified?
   */
  boolean isEnabled();

  /**
   * Takes a {@link BlobStore} and creates a {@link BlobStoreGroup} that contains the original blob store
   *
   * @param from a {@link BlobStore} to be "promoted"
   * @return {@link BlobStoreGroup} that contains the original blob store
   */
  BlobStoreGroup promote(final BlobStore from);
}
