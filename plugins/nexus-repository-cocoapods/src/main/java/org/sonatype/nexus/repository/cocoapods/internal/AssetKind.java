package org.sonatype.nexus.repository.cocoapods.internal;

import org.sonatype.nexus.repository.cache.CacheControllerHolder.CacheType;

import static org.sonatype.nexus.repository.cache.CacheControllerHolder.CONTENT;
import static org.sonatype.nexus.repository.cache.CacheControllerHolder.METADATA;

/**
 * @since 3.19
 */
public enum AssetKind
{
  SPEC(METADATA),
  CDN_METADATA(METADATA),
  POD(CONTENT);

  private final CacheType cacheType;

  AssetKind(final CacheType cacheType) {
    this.cacheType = cacheType;
  }

  public CacheType getCacheType() {
    return cacheType;
  }
}
