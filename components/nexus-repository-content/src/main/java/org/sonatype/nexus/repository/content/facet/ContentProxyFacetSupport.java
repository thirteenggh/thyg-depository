package org.sonatype.nexus.repository.content.facet;

import java.io.IOException;

import org.sonatype.nexus.repository.cache.CacheInfo;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.proxy.ProxyFacet;
import org.sonatype.nexus.repository.proxy.ProxyFacetSupport;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;

/**
 * Content {@link ProxyFacet} support.
 *
 * @since 3.25
 */
public abstract class ContentProxyFacetSupport
    extends ProxyFacetSupport
{
  @Override
  protected void indicateVerified(
      final Context context,
      final Content content,
      final CacheInfo cacheInfo) throws IOException
  {
    // refresh internal cache details to record that we know this asset is up-to-date
    Asset asset = content.getAttributes().get(Asset.class);
    if (asset != null) {
      facet(ContentFacet.class).assets().with(asset).markAsCached(cacheInfo);
    }
    else {
      log.debug("Proxied content has no attached asset; cannot refresh cache details");
    }
  }
}
