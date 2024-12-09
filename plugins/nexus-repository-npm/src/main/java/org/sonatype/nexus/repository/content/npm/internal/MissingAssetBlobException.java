package org.sonatype.nexus.repository.content.npm.internal;

import org.sonatype.nexus.repository.MissingBlobException;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.AssetBlob;

/**
 * Thrown when attempting to access blob content, for an existing asset, which is now missing from the blobstore.
 *
 * @since 3.28
 */
public class MissingAssetBlobException
    extends MissingBlobException
{
  private final Asset asset;

  public MissingAssetBlobException(final Asset asset) {
    super(asset.blob().map(AssetBlob::blobRef).orElse(null));
    this.asset = asset;
  }

  public Asset getAsset() {
    return asset;
  }
}
