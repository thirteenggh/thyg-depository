package org.sonatype.nexus.blobstore.restore;

import java.util.Properties;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.repository.Repository;

/**
 * @since 3.4
 */
public interface RestoreBlobStrategy
{
  /**
   * @deprecated since 3.6
   */
  @Deprecated
  default void restore(Properties properties, Blob blob, BlobStore blobStore) {
    restore(properties, blob, blobStore, false);
  }

  /**
   * @since 3.6
   *
   * @param properties associated with the blob being restore
   * @param blob being restored
   * @param blobStore the blob store where the blob will be stored
   * @param isDryRun if {@code true}, no lasting changes will be made, only logged
   */
  void restore(Properties properties, Blob blob, BlobStore blobStore, boolean isDryRun);

  /**
   * Runs after all blobs have been restored to the database.
   * 
   * @since 3.15
   * @param updateAssets whether updating assets is expected or not
   * @param repository repository to update
   */
  void after(boolean updateAssets, final Repository repository);
}
