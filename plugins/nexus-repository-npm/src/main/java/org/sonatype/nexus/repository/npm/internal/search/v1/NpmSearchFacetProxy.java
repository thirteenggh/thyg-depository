package org.sonatype.nexus.repository.npm.internal.search.v1;

import java.io.IOException;

import javax.inject.Named;

import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.npm.internal.NpmProxyFacet.ProxyTarget;
import org.sonatype.nexus.repository.proxy.ProxyFacet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Parameters;
import org.sonatype.nexus.repository.view.Request;

import static org.sonatype.nexus.repository.http.HttpMethods.GET;

/**
 * Implementation of {@code NpmSearchFacet} for proxy repositories.
 *
 * @since 3.7
 */
@Named
public class NpmSearchFacetProxy
    extends FacetSupport
    implements NpmSearchFacet
{
  private static final String REPOSITORY_SEARCH_ASSET = "-/v1/search";

  @Override
  public Content searchV1(final Parameters parameters) throws IOException {
    try {
      final Request getRequest = new Request.Builder()
          .action(GET)
          .path("/" + REPOSITORY_SEARCH_ASSET)
          .parameters(parameters)
          .build();
      Context context = new Context(getRepository(), getRequest);
      context.getAttributes().set(ProxyTarget.class, ProxyTarget.SEARCH_V1_RESULTS);
      Content searchResults = getRepository().facet(ProxyFacet.class).get(context);
      if (searchResults == null) {
        throw new IOException("Could not retrieve registry search");
      }
      return searchResults;
    }
    catch (Exception e) {
      throw new IOException(e);
    }
  }
}
