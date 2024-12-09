package org.sonatype.nexus.repository.r.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.r.internal.AssetKind;
import org.sonatype.nexus.repository.r.internal.RFormat;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetBlob;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter;
import org.sonatype.nexus.repository.storage.Query;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.BlobPayload;

import com.google.common.collect.ImmutableList;

import static java.util.Collections.singletonList;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;
import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;
import static org.sonatype.nexus.repository.storage.ComponentEntityAdapter.P_GROUP;
import static org.sonatype.nexus.repository.storage.ComponentEntityAdapter.P_VERSION;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_ATTRIBUTES;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_NAME;
import static org.sonatype.nexus.repository.storage.Query.builder;

/**
 * Shared code between R facets.
 *
 * @since 3.28
 */
public final class RFacetUtils
{
  public static final List<HashAlgorithm> HASH_ALGORITHMS = ImmutableList.of(SHA1);

  private RFacetUtils() {
    // empty
  }

  /**
   * Convert an asset blob to {@link Content}.
   *
   * @return content of asset blob
   */
  public static Content toContent(final Asset asset, final Blob blob) {
    Content content = new Content(new BlobPayload(blob, asset.requireContentType()));
    Content.extractFromAsset(asset, HASH_ALGORITHMS, content.getAttributes());
    return content;
  }

  /**
   * Find a component by its name, tag (version) and group
   *
   * @return found component of null if not found
   */
  @Nullable
  public static Component findComponent(final StorageTx tx,
                                        final Repository repository,
                                        final String name,
                                        final String version,
                                        final String group)
  {
    Iterable<Component> components = tx.findComponents(
        Query.builder()
            .where(P_NAME).eq(name)
            .and(P_VERSION).eq(version)
            .and(P_GROUP).eq(group)
            .build(),
        singletonList(repository)
    );
    if (components.iterator().hasNext()) {
      return components.iterator().next();
    }
    return null;
  }

  /**
   * Find an asset by its name.
   *
   * @return found asset or null if not found
   */
  @Nullable
  public static Asset findAsset(final StorageTx tx, final Bucket bucket, final String assetName) {
    return tx.findAssetWithProperty(MetadataNodeEntityAdapter.P_NAME, assetName, bucket);
  }

  /**
   * Browse all assets in bucket by asset kind
   *
   * @return {@link Iterable} of assets or empty one
   */
  public static Iterable<Asset> browseAllAssetsByKind(final StorageTx tx,
                                                      final Bucket bucket,
                                                      final AssetKind assetKind)
  {
    final Query query = builder()
        .where(P_ATTRIBUTES + "." + RFormat.NAME + "." + P_ASSET_KIND)
        .eq(assetKind.name())
        .build();
    return tx.browseAssets(query, bucket);
  }

  /**
   * Save an asset && create blob.
   *
   * @return blob content
   */
  public static Content saveAsset(final StorageTx tx,
                                  final Asset asset,
                                  final Supplier<InputStream> contentSupplier,
                                  final Payload payload) throws IOException
  {
    AttributesMap contentAttributes = null;
    String contentType = null;
    if (payload instanceof Content) {
      contentAttributes = ((Content) payload).getAttributes();
      contentType = payload.getContentType();
    }
    return saveAsset(tx, asset, contentSupplier, contentType, contentAttributes);
  }

  /**
   * Save an asset && create blob.
   *
   * @return blob content
   */
  public static Content saveAsset(final StorageTx tx,
                                  final Asset asset,
                                  final Supplier<InputStream> contentSupplier,
                                  final String contentType,
                                  @Nullable final AttributesMap contentAttributes) throws IOException
  {
    Content.applyToAsset(asset, Content.maintainLastModified(asset, contentAttributes));
    AssetBlob assetBlob = tx.setBlob(
        asset, asset.name(), contentSupplier, HASH_ALGORITHMS, null, contentType, false
    );

    asset.markAsDownloaded();
    tx.saveAsset(asset);
    return toContent(asset, assetBlob.getBlob());
  }

  /**
   * Extracts {@link AssetKind} from asset attributes.
   *
   * @return {@link AssetKind} of this asset.
   */
  public static AssetKind extractAssetKind(final Asset asset) {
    return AssetKind.valueOf(asset.formatAttributes().get(P_ASSET_KIND, String.class));
  }
}
