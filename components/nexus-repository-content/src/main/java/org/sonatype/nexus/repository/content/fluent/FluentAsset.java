package org.sonatype.nexus.repository.content.fluent;

import java.time.OffsetDateTime;
import java.util.Map;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.cache.CacheController;
import org.sonatype.nexus.repository.cache.CacheInfo;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.AttributeChangeSet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import com.google.common.hash.HashCode;

/**
 * Fluent API for a particular asset.
 *
 * @since 3.21
 */
public interface FluentAsset
    extends Asset, FluentAttributes<FluentAsset>
{
  /**
   * The repository containing this asset.
   *
   * @since 3.24
   */
  Repository repository();

  /**
   * Converts a temporary blob into a permanent blob and attaches it to this asset.
   */
  FluentAsset attach(TempBlob blob);

  /**
   * Attaches an existing blob to this asset.
   */
  FluentAsset attach(Blob blob, Map<HashAlgorithm, HashCode> checksums);

  /**
   * Downloads this asset.
   */
  Content download();

  /**
   * Mark this asset as recently downloaded by a user action.
   */
  FluentAsset markAsDownloaded();

  /**
   * Mark this proxied asset as recently cached from the given content.
   *
   * @since 3.25
   */
  FluentAsset markAsCached(Payload content);

  /**
   * Mark this generated asset as recently cached.
   *
   * @since 3.24
   */
  FluentAsset markAsCached(CacheInfo cacheInfo);

  /**
   * Mark this generated/proxied asset as stale.
   *
   * @since 3.24
   */
  FluentAsset markAsStale();

  /**
   * Is this generated/proxied asset stale?
   *
   * @since 3.24
   */
  boolean isStale(CacheController cacheController);

  /**
   * Update this asset to have the given kind.
   *
   * @since 3.25
   */
  FluentAsset kind(String kind);

  /**
   * Deletes this asset.
   */
  boolean delete();

  /**
   * Generally it is recommended that this method not be called and let stores manage this value.
   *
   * @since 3.29
   */
  void blobCreated(OffsetDateTime blobCreated);

  /**
   * Generally it is recommended that this method not be called and let stores manage this value automatically.
   *
   * Sets the created time of the asset associated with the ID to the specified time.
   *
   * @since 3.29
   */
  void created(OffsetDateTime created);

  /**
   * Generally it is recommended that this method not be called and let stores manage this value automatically.
   *
   * Sets the last download time of the asset associated with the ID to the specified time.
   *
   * @since 3.29
   */
  void lastDownloaded(OffsetDateTime lastDownloaded);

  /**
   * Generally it is recommended that this method not be called and let stores manage this value automatically.
   *
   * Sets the last updated time of the asset associated with the ID to the specified time.
   *
   * @since 3.29
   */
  void lastUpdated(OffsetDateTime lastUpdated);

  /**
   * Applies the given change to the current attributes.
   */
  FluentAsset attributes(AttributeChangeSet changeset);
}
