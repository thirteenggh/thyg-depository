package org.sonatype.nexus.repository.r.internal;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.cache.CacheControllerHolder;
import org.sonatype.nexus.repository.cache.CacheControllerHolder.CacheType;

/**
 * Asset kinds for R.
 *
 * @since 3.28
 */
public enum AssetKind
{
  PACKAGES(CacheControllerHolder.METADATA),
  RDS_METADATA(CacheControllerHolder.METADATA),
  ARCHIVE(CacheControllerHolder.CONTENT);

  private final CacheType cacheType;

  AssetKind(final CacheType cacheType) {
    this.cacheType = cacheType;
  }

  @Nonnull
  public CacheType getCacheType() {
    return cacheType;
  }
}
