package org.sonatype.nexus.repository.golang;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.cache.CacheControllerHolder;
import org.sonatype.nexus.repository.cache.CacheControllerHolder.CacheType;

/**
 * Asset kinds for go
 *
 * @since 3.17
 */
public enum AssetKind
{
  PACKAGE(CacheControllerHolder.CONTENT),
  INFO(CacheControllerHolder.METADATA),
  LIST(CacheControllerHolder.METADATA),
  LATEST(CacheControllerHolder.METADATA),
  MODULE(CacheControllerHolder.METADATA);

  private final CacheType cacheType;

  AssetKind(final CacheType cacheType) {
    this.cacheType = cacheType;
  }

  @Nonnull
  public CacheType getCacheType() {
    return cacheType;
  }
}
