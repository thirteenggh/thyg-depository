package org.sonatype.nexus.repository.npm.internal;

import javax.annotation.Nonnull;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.cache.CacheControllerHolder;
import org.sonatype.nexus.repository.cache.CacheControllerHolder.CacheType;

import static com.google.common.base.Preconditions.checkNotNull;

@Facet.Exposed
public interface NpmProxyFacet
    extends Facet
{

  void invalidateProxyCaches();

  enum ProxyTarget
  {
    SEARCH_INDEX(CacheControllerHolder.METADATA),
    SEARCH_V1_RESULTS(CacheControllerHolder.METADATA),
    PACKAGE(CacheControllerHolder.METADATA),
    DIST_TAGS(CacheControllerHolder.METADATA),
    TARBALL(CacheControllerHolder.CONTENT);

    private final CacheType cacheType;

    ProxyTarget(final CacheType cacheType) {
      this.cacheType = checkNotNull(cacheType);
    }

    @Nonnull
    public CacheType getCacheType() {
      return cacheType;
    }
  }
}
