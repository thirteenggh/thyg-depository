package org.sonatype.nexus.orient.raw;

import java.io.IOException;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.cache.CacheInfo;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetBlob;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Payload;

/**
 * Provides persistent storage for {@link Content}.
 *
 * @since 3.0
 */
@Facet.Exposed
public interface RawContentFacet
    extends Facet
{
  @Nullable
  Content get(String path) throws IOException;

  Content put(String path, Payload content) throws IOException;

  /**
   * Accepts an {@link AssetBlob}, creates an {@link Asset} if one doesn't already exist at {@code path},
   * and returns it.
   *
   * @since 3.15
   */
  Asset put(String path, AssetBlob assetBlob, @Nullable AttributesMap contentAttributes);

  boolean delete(String path) throws IOException;

  /**
   * Raw proxy facet specific method: invoked when cached content (returned by {@link #get(String)} method of this
   * same facet instance) is found to be up to date after remote checks. This method applies the passed in {@link
   * CacheInfo} to the {@link Content}'s underlying asset.
   */
  void setCacheInfo(String path, Content content, CacheInfo cacheInfo) throws IOException;

  Asset getOrCreateAsset(Repository repository, String componentName, String componentGroup, String assetName);

  /*
   * Check for the existence of an {@link Asset} with {@code name}.
   *
   * @since 3.15
   */
  boolean assetExists(String name);
}
