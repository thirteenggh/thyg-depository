package org.sonatype.nexus.repository.conda.internal;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.cache.CacheControllerHolder.CacheType;

import static org.sonatype.nexus.repository.cache.CacheControllerHolder.CONTENT;
import static org.sonatype.nexus.repository.cache.CacheControllerHolder.METADATA;

/**
 * @since 3.19
 */
public enum AssetKind
{
  CHANNEL_INDEX_HTML(METADATA),
  CHANNEL_DATA_JSON(METADATA),
  CHANNEL_RSS_XML(METADATA),
  ARCH_INDEX_HTML(METADATA),
  ARCH_REPODATA_JSON(METADATA),
  ARCH_REPODATA_JSON_BZ2(METADATA),
  ARCH_REPODATA2_JSON(METADATA),
  ARCH_TAR_PACKAGE(CONTENT),
  ARCH_CONDA_PACKAGE(CONTENT);

  private final CacheType cacheType;

  AssetKind(final CacheType cacheType) {
    this.cacheType = cacheType;
  }

  @Nonnull
  public CacheType getCacheType() {
    return cacheType;
  }
}
