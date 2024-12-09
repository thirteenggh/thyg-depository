package org.sonatype.nexus.repository.npm.internal.search.legacy;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Named;

import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.npm.internal.NpmProxyFacet.ProxyTarget;
import org.sonatype.nexus.repository.proxy.ProxyFacet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Request;

import org.joda.time.DateTime;

import static org.sonatype.nexus.repository.http.HttpMethods.GET;

/**
 * npm search index facet for proxy repositories: it is getting index document from proxy cache.
 *
 * @since 3.0
 * @deprecated No longer actively used by npm upstream, replaced by v1 search api (NEXUS-13150).
 */
@Deprecated
@Named
public class NpmSearchIndexFacetProxy
    extends FacetSupport
    implements NpmSearchIndexFacet
{

  public static final String REPOSITORY_ROOT_ASSET = "-/all";

  @Nonnull
  @Override
  public Content searchIndex(@Nullable final DateTime since) throws IOException {
    try {
      final Request getRequest = new Request.Builder()
          .action(GET)
          .path("/" + REPOSITORY_ROOT_ASSET)
          .build();
      Context context = new Context(getRepository(), getRequest);
      context.getAttributes().set(ProxyTarget.class, ProxyTarget.SEARCH_INDEX);
      Content fullIndex = getRepository().facet(ProxyFacet.class).get(context);
      if (fullIndex == null) {
        throw new IOException("Could not retrieve registry root");
      }
      return NpmSearchIndexFilter.filterModifiedSince(fullIndex, since);
    }
    catch (Exception e) {
      throw new IOException(e);
    }
  }

  @Override
  public void invalidateCachedSearchIndex() {
    // nop, proxy index is subject of proxy caching logic
  }
}
