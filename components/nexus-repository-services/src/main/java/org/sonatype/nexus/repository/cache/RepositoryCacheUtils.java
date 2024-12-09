package org.sonatype.nexus.repository.cache;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.proxy.ProxyFacet;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.ProxyType;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility class for consolidating repeated cache-related logic not exclusive to individual facets and components.
 *
 * @since 3.0
 */
public final class RepositoryCacheUtils
{
  private RepositoryCacheUtils() {
    // empty
  }

  /**
   * Invalidates the group or proxy caches of the specified repository based on type.
   *
   * This is a no-op for hosted repositories.
   */
  public static void invalidateCaches(final Repository repository) {
    checkNotNull(repository);
    if (GroupType.NAME.equals(repository.getType().getValue())) {
      invalidateGroupCaches(repository);
    } else if (ProxyType.NAME.equals(repository.getType().getValue())) {
      invalidateProxyAndNegativeCaches(repository);
    }
  }

  /**
   * Invalidates the group caches for given repository.
   */
  public static void invalidateGroupCaches(final Repository repository) {
    checkNotNull(repository);
    checkArgument(GroupType.NAME.equals(repository.getType().getValue()));
    GroupFacet groupFacet = repository.facet(GroupFacet.class);
    groupFacet.invalidateGroupCaches();
  }

  /**
   * Invalidates the proxy and negative caches for given repository.
   */
  public static void invalidateProxyAndNegativeCaches(final Repository repository) {
    checkNotNull(repository);
    checkArgument(ProxyType.NAME.equals(repository.getType().getValue()));
    ProxyFacet proxyFacet = repository.facet(ProxyFacet.class);
    proxyFacet.invalidateProxyCaches();
    NegativeCacheFacet negativeCacheFacet = repository.facet(NegativeCacheFacet.class);
    negativeCacheFacet.invalidate();
  }
}
