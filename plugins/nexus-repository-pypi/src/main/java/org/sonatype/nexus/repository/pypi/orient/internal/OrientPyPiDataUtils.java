package org.sonatype.nexus.repository.pypi.orient.internal;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Repository;
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
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Collections.singletonList;
import static org.sonatype.nexus.repository.pypi.internal.PyPiAttributes.SUPPORTED_ATTRIBUTES;
import static org.sonatype.nexus.repository.pypi.internal.PyPiStorageUtils.HASH_ALGORITHMS;
import static org.sonatype.nexus.repository.storage.ComponentEntityAdapter.P_VERSION;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_NAME;

/**
 * Utility methods for working with PyPI data.
 *
 * @since 3.1
 */
public final class OrientPyPiDataUtils
{
  /**
   * Convert an asset blob to {@link Content}.
   *
   * @return content of asset blob
   */
  static Content toContent(final Asset asset, final Blob blob) {
    Content content = new Content(new BlobPayload(blob, asset.requireContentType()));
    Content.extractFromAsset(asset, HASH_ALGORITHMS, content.getAttributes());
    return content;
  }

  /**
   * Find a component by its name and tag (version)
   *
   * @return found component of null if not found
   */
  @Nullable
  static Component findComponent(
      final StorageTx tx,
      final Repository repository,
      final String name,
      final String version)
  {
    Iterable<Component> components = tx.findComponents(
        Query.builder()
            .where(P_NAME).eq(name)
            .and(P_VERSION).eq(version)
            .build(),
        singletonList(repository)
    );
    if (components.iterator().hasNext()) {
      return components.iterator().next();
    }
    return null;
  }

  /**
   * Find whether or not any component with the specified name exists. If true, we can assume that the Python package
   * was "registered" and should not 404 (even if no assets have been uploaded). If false, we should likely 404.
   */
  static boolean findComponentExists(final StorageTx tx, final Repository repository, final String name) {
    return tx.countComponents(Query.builder()
            .where(P_NAME).eq(name)
            .build(),
        singletonList(repository)) > 0;
  }

  /**
   * Finds all the assets for a particular component name.
   *
   * @return list of assets
   */
  static Iterable<Asset> findAssetsByComponentName(final StorageTx tx, final Repository repository, final String name)
  {
    return tx.findAssets(
        Query.builder()
            .where("component.name").eq(name)
            .suffix("order by name desc")
            .build(),
        singletonList(repository)
    );
  }

  /**
   * Find an asset by its name.
   *
   * @return found asset or null if not found
   */
  @Nullable
  static Asset findAsset(final StorageTx tx, final Bucket bucket, final String assetName) {
    return tx.findAssetWithProperty(MetadataNodeEntityAdapter.P_NAME, assetName, bucket);
  }

  /**
   * Save an asset and create blob.
   *
   * @return blob content
   */
  static Content saveAsset(
      final StorageTx tx,
      final Asset asset,
      final TempBlob tempBlob,
      final Payload payload) throws IOException
  {
    AttributesMap contentAttributes = null;
    String contentType = null;
    if (payload instanceof Content) {
      contentAttributes = ((Content) payload).getAttributes();
      contentType = payload.getContentType();
    }
    return saveAsset(tx, asset, tempBlob, contentType, contentAttributes);
  }

  /**
   * Save an asset and create a blob with the specified content attributes.
   *
   * @return blob content
   */
  static Content saveAsset(
      final StorageTx tx,
      final Asset asset,
      final TempBlob tempBlob,
      @Nullable final String contentType,
      @Nullable final AttributesMap contentAttributes) throws IOException
  {
    Content.applyToAsset(asset, Content.maintainLastModified(asset, contentAttributes));
    AssetBlob assetBlob = tx.setBlob(asset, asset.name(), tempBlob, null, contentType, false);

    tx.saveAsset(asset);
    return toContent(asset, assetBlob.getBlob());
  }

  /**
   * Copies PyPI attributes from a map into the format attributes for the asset. We put almost all the format info on
   * the asset, not the component. While most should not differ between uploads for the same name and version, it is
   * possible, so mitigate by associating with assets.
   */
  static void copyAttributes(final Asset asset, final Map<String, String> attributes) {
    checkNotNull(asset);
    checkNotNull(attributes);
    for (String attribute : SUPPORTED_ATTRIBUTES) {
      String value = Strings.nullToEmpty(attributes.get(attribute)).trim();
      if (!value.isEmpty()) {
        asset.formatAttributes().set(attribute, value);
      }
    }
  }

  private OrientPyPiDataUtils() {
    // empty
  }
}
