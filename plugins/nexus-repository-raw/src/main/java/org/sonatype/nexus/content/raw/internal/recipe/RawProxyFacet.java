package org.sonatype.nexus.content.raw.internal.recipe;

import java.io.IOException;

import javax.inject.Named;

import org.sonatype.nexus.content.raw.RawContentFacet;
import org.sonatype.nexus.repository.content.facet.ContentProxyFacetSupport;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;

/**
 * Raw proxy facet.
 *
 * @since 3.24
 */
@Named
public class RawProxyFacet
    extends ContentProxyFacetSupport
{
  @Override
  protected Content getCachedContent(final Context context) throws IOException {
    return content().get(assetPath(context)).orElse(null);
  }

  @Override
  protected Content store(final Context context, final Content payload) throws IOException {
    return content().put(assetPath(context), payload);
  }

  @Override
  protected String getUrl(final Context context) {
    return removeSlashPrefix(assetPath(context));
  }

  private RawContentFacet content() {
    return getRepository().facet(RawContentFacet.class);
  }

  /**
   * Determines what 'asset' this request relates to.
   */
  private String assetPath(final Context context) {
    final TokenMatcher.State tokenMatcherState = context.getAttributes().require(TokenMatcher.State.class);
    return tokenMatcherState.getTokens().get(RawProxyRecipe.PATH_NAME);
  }

  private String removeSlashPrefix(final String url) {
    return url != null && url.startsWith("/") ? url.substring(1) : url;
  }
}
