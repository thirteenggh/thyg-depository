package org.sonatype.nexus.content.maven.internal.recipe;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Named;

import org.sonatype.nexus.content.maven.MavenContentFacet;
import org.sonatype.nexus.repository.cache.CacheController;
import org.sonatype.nexus.repository.content.facet.ContentProxyFacetSupport;
import org.sonatype.nexus.repository.maven.LayoutPolicy;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.internal.Constants;
import org.sonatype.nexus.repository.proxy.ProxyFacetSupport;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;

/**
 * Maven specific implementation of {@link ProxyFacetSupport}.
 *
 * @since 3.26
 */
@Named
public class MavenProxyFacet
    extends ContentProxyFacetSupport
{
  @Override
  protected Content getCachedContent(final Context context) throws IOException {
    return content()
        .get(mavenPath(context))
        .orElse(null);
  }

  @Nullable
  @Override
  protected Content fetch(final Context context, final Content stale) throws IOException {
    // do not go remote for a non maven 2 artifact or metadata if is not already present in cache or allowed by config
    MavenContentFacet mavenFacet = content();
    if (stale == null && mavenFacet.layoutPolicy() == LayoutPolicy.STRICT) {
      MavenPath mavenPath = mavenPath(context);
      if (mavenPath.getCoordinates() == null
          && !mavenFacet.getMavenPathParser().isRepositoryMetadata(mavenPath)
          && !mavenFacet.getMavenPathParser().isRepositoryIndex(mavenPath)
          && !mavenPath.getFileName().equals(Constants.ARCHETYPE_CATALOG_FILENAME)) {
        return null;
      }
    }
    return super.fetch(context, stale);
  }

  @Override
  protected CacheController getCacheController(@Nonnull final Context context) {
    if (content().getMavenPathParser().isRepositoryMetadata(mavenPath(context))) {
      return cacheControllerHolder.getMetadataCacheController();
    }
    else {
      return cacheControllerHolder.getContentCacheController();
    }
  }

  @Override
  protected Content store(final Context context, final Content payload) throws IOException {
    return content().put(mavenPath(context), payload);
  }

  @Override
  protected String getUrl(@Nonnull final Context context) {
    return removePrefixingSlash(context.getRequest().getPath());
  }

  @Nonnull
  private MavenPath mavenPath(@Nonnull final Context context) {
    return context.getAttributes().require(MavenPath.class);
  }

  private MavenContentFacet content() {
    return getRepository().facet(MavenContentFacet.class);
  }

  private String removePrefixingSlash(final String url) {
    if(url != null && url.startsWith("/")) {
      return url.replaceFirst("/", "");
    }
    return url;
  }
}
