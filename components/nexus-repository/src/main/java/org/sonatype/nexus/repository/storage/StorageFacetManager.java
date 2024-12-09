package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.Bucket;

/**
 * Contains StorageFacet-related operations that cannot be represented in individual StorageFacet instances.
 *
 * @since 3.2.1
 */
public interface StorageFacetManager
{
  /**
   * Enqueues the contents (bucket) of a particular StorageFacet for deletion. Called by a StorageFacet on delete.
   */
  void enqueueDeletion(Repository repository, BlobStore blobStore, Bucket bucket);

  /**
   * Performs the actual deletions for any deleted storage facets, returning the number of deletions successfully
   * performed. This can potentially be a long-running operation!
   *
   * @return the number of successful cleanups performed during the call
   */
  long performDeletions();
}
