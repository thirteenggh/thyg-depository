package org.sonatype.nexus.blobstore.restore.orient;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.Asset;

/**
 * Strategy for checking the integrity of the assets in a repository against its blobstore
 *
 * @since 3.6.1
 */
public interface OrientIntegrityCheckStrategy
{
  /**
   * Run the integrity check on the given repository and blob store
   *
   * @param repository  repository to check
   * @param blobStore   blob store to check
   * @param isCancelled Supplier to check during processing if the task is cancelled
   * @param integrityCheckFailedHandler will be called with Asset if unable to validate blob integrity
   */
  void check(
      final Repository repository,
      final BlobStore blobStore,
      final Supplier<Boolean> isCancelled,
      final Consumer<Asset> integrityCheckFailedHandler);
}
