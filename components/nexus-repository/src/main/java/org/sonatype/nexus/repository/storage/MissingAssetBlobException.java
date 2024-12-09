package org.sonatype.nexus.repository.storage;

/**
 * Thrown when attempting to access blob content ,for an existing asset, which is now missing from the blobstore.
 *
 * @since 3.16
 */
public class MissingAssetBlobException
    extends MissingBlobException
{
  private final Asset asset;

  public MissingAssetBlobException(final Asset asset) {
    super(asset.blobRef());
    this.asset = asset;
  }

  public Asset getAsset() {
    return asset;
  }
}
