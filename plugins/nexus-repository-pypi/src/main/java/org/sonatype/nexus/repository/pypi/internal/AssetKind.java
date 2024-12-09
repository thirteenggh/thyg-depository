package org.sonatype.nexus.repository.pypi.internal;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.cache.CacheControllerHolder;
import org.sonatype.nexus.repository.cache.CacheControllerHolder.CacheType;

/**
 * Asset kinds for PyPI.
 *
 * @since 3.1
 */
public enum AssetKind
{
  ROOT_INDEX(CacheControllerHolder.METADATA),
  INDEX(CacheControllerHolder.METADATA),
  PACKAGE(CacheControllerHolder.CONTENT),
  SEARCH(CacheControllerHolder.METADATA),
  PACKAGE_SIGNATURE(CacheControllerHolder.METADATA);

  private final CacheType cacheType;

  AssetKind(final CacheType cacheType) {
    this.cacheType = cacheType;
  }

  @Nonnull
  public CacheType getCacheType() {
    return cacheType;
  }
}
