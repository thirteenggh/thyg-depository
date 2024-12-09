package org.sonatype.nexus.repository.p2.internal;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.cache.CacheControllerHolder;
import org.sonatype.nexus.repository.cache.CacheControllerHolder.CacheType;

/**
 * Asset kinds for P2.
 *
 * Examples of all can be found at: http://download.eclipse.org/technology/m2e/releases/1.7/1.7.0.20160603-1933/?d
 */
public enum AssetKind
{
  P2_INDEX(CacheControllerHolder.METADATA),
  ARTIFACTS_METADATA(CacheControllerHolder.METADATA),
  CONTENT_METADATA(CacheControllerHolder.METADATA),
  COMPOSITE_ARTIFACTS(CacheControllerHolder.METADATA),
  COMPOSITE_CONTENT(CacheControllerHolder.METADATA),
  BUNDLE(CacheControllerHolder.CONTENT),
  BINARY_BUNDLE(CacheControllerHolder.CONTENT);

  private final CacheType cacheType;

  AssetKind(final CacheType cacheType) {
    this.cacheType = cacheType;
  }

  @Nonnull
  public CacheType getCacheType() {
    return cacheType;
  }
}
