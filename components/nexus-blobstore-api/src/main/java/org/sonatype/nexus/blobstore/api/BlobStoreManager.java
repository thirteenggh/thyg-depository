package org.sonatype.nexus.blobstore.api;

import java.util.Optional;

import javax.annotation.Nullable;

import org.sonatype.goodies.lifecycle.Lifecycle;

/**
 * {@link BlobStore} manager.
 *
 * @since 3.0
 */
public interface BlobStoreManager
  extends Lifecycle
{
  /**
   * Default blob store name.
   */
  String DEFAULT_BLOBSTORE_NAME = "default";

  /**
   * @return all BlobStores
   */
  Iterable<BlobStore> browse();

  /**
   * Create a new BlobStore
   */
  BlobStore create(BlobStoreConfiguration blobStoreConfiguration) throws Exception;

  /**
   * Update an existing BlobStore
   *
   * @since 3.14
   */
  BlobStore update(BlobStoreConfiguration blobStoreConfiguration) throws Exception;

  /**
   * Lookup a BlobStore by name
   */
  @Nullable
  BlobStore get(String name);

  /**
   * Delete a BlobStore by name
   */
  void delete(String name) throws Exception;

  /**
   * Delete a BlobStore by name, even if it is use.
   *
   * @since 3.14
   */
  void forceDelete(String name) throws Exception;

  /**
   * Returns true if a blob store with the provided name already exists. Check is case-insensitive.
   *
   * @since 3.1
   */
  boolean exists(String name);

  /**
   * Returns the number of other blob stores that use the named blob store.
   *
   * @since 3.14
   */
  long blobStoreUsageCount(String blobStoreName);

  /**
   * Returns true if the blob store is promotable
   * @param blobStoreName
   * @return true if member is promotable
   *
   * @since 3.15
   */
  boolean isPromotable(String blobStoreName);

  /**
   * Returns the parent group of the blob store if it exists
   * @param blobStoreName
   * @return {@link java.util.Optional<String>} containing the parent group name if it exists
   *
   * @since 3.15
   */
  Optional<String> getParent(String blobStoreName);

  /**
   * @return an empty {@link BlobStoreConfiguration} for use with this manager
   *
   * @since 3.20
   */
  BlobStoreConfiguration newConfiguration();

  /**
   * Moves a blob from one blobstore to another
   * @param blobId
   * @param srcBlobStore
   * @param destBlobStore
   */
  Blob moveBlob(final BlobId blobId, final BlobStore srcBlobStore, final BlobStore destBlobStore);
}
