package org.sonatype.nexus.repository.content.store;

import java.time.OffsetDateTime;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.repository.content.AssetBlob;
import org.sonatype.nexus.transaction.Transactional;

import com.google.inject.assistedinject.Assisted;

/**
 * {@link AssetBlob} store.
 *
 * @since 3.21
 */
@Named
public class AssetBlobStore<T extends AssetBlobDAO>
    extends ContentStoreSupport<T>
{
  @Inject
  public AssetBlobStore(final DataSessionSupplier sessionSupplier,
                        @Assisted final String contentStoreName,
                        @Assisted final Class<T> daoClass)
  {
    super(sessionSupplier, contentStoreName, daoClass);
  }

  /**
   * Browse unused asset blobs in the content data store in a paged fashion.
   *
   * @param limit maximum number of asset blobs to return
   * @param continuationToken optional token to continue from a previous request
   * @return collection of asset blobs and the next continuation token
   *
   * @see Continuation#nextContinuationToken()
   */
  @Transactional
  public Continuation<AssetBlob> browseUnusedAssetBlobs(final int limit, @Nullable final String continuationToken) {
    return dao().browseUnusedAssetBlobs(limit, continuationToken);
  }

  /**
   * Browse asset blobs in the content data store in a paged fashion.
   *
   * @param limit maximum number of asset blobs to return
   * @param continuationToken optional token to continue from a previous request
   * @return collection of asset blobs and the next continuation token
   *
   * @see Continuation#nextContinuationToken()
   */
  @Transactional
  public Continuation<AssetBlob> browseAssetBlobs(final int limit, @Nullable final String continuationToken) {
    return dao().browseAssetBlobs(limit, continuationToken);
  }

  /**
   * Creates the given asset blob in the content data store.
   *
   * @param assetBlob the asset blob to create
   */
  @Transactional
  public void createAssetBlob(final AssetBlobData assetBlob) {
    dao().createAssetBlob(assetBlob);
  }

  /**
   * Retrieves an asset blob from the content data store.
   *
   * @param blobRef the blob reference
   * @return asset blob if it was found
   */
  @Transactional
  public Optional<AssetBlob> readAssetBlob(final BlobRef blobRef) {
    return dao().readAssetBlob(blobRef);
  }

  /**
   * Deletes an asset blob from the content data store.
   *
   * @param blobRef the blob reference
   * @return {@code true} if the asset blob was deleted
   */
  @Transactional
  public boolean deleteAssetBlob(final BlobRef blobRef) {
    return dao().deleteAssetBlob(blobRef);
  }

  /**
   * Generally it is recommended that this method not be called and let stores manage this value.
   *
   * @since 3.29
   */
  @Transactional
  public void setBlobCreated(final AssetBlob blob, final OffsetDateTime blobCreated) {
    dao().setBlobCreated(blob.blobRef(), blobCreated);
  }
}
