package org.sonatype.nexus.repository.content.store;

import java.time.OffsetDateTime;
import java.util.Optional;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.datastore.api.ContentDataAccess;
import org.sonatype.nexus.datastore.api.SchemaTemplate;
import org.sonatype.nexus.repository.content.AssetBlob;

import org.apache.ibatis.annotations.Param;

/**
 * Asset blob {@link ContentDataAccess}.
 *
 * @since 3.20
 */
@SchemaTemplate("format")
public interface AssetBlobDAO
    extends ContentDataAccess
{
  /**
   * Browse unused asset blobs in the content data store in a paged fashion.
   *
   * @param limit maximum number of asset blobs to return
   * @param continuationToken optional token to continue from a previous request
   * @return collection of asset blobs and the next continuation token
   *
   * @see Continuation#nextContinuationToken()
   */
  Continuation<AssetBlob> browseUnusedAssetBlobs(
      @Param("limit") int limit,
      @Param("continuationToken") @Nullable String continuationToken);

  /**
   * Browse asset blobs in the content data store in a paged fashion.
   *
   * @param limit maximum number of asset blobs to return
   * @param continuationToken optional token to continue from a previous request
   * @return collection of asset blobs and the next continuation token
   *
   * @see Continuation#nextContinuationToken()
   */
  Continuation<AssetBlob> browseAssetBlobs(
      @Param("limit") int limit,
      @Param("continuationToken") @Nullable String continuationToken);

  /**
   * Creates the given asset blob in the content data store.
   *
   * @param assetBlob the asset blob to create
   */
  void createAssetBlob(AssetBlobData assetBlob);

  /**
   * Retrieves an asset blob from the content data store.
   *
   * @param blobRef the blob reference
   * @return asset blob if it was found
   */
  Optional<AssetBlob> readAssetBlob(@Param("blobRef") BlobRef blobRef);

  /**
   * Deletes an asset blob from the content data store.
   *
   * @param blobRef the blob reference
   * @return {@code true} if the asset blob was deleted
   */
  boolean deleteAssetBlob(@Param("blobRef") BlobRef blobRef);

  /**
   * Generally it is recommended that this method not be called and let stores manage this value.
   *
   * @since 3.29
   */
  void setBlobCreated(@Param("blobRef") BlobRef blobRef, @Param("blobCreated") OffsetDateTime blobCreated);
}
