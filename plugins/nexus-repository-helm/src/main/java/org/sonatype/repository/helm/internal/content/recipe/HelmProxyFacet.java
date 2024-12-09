package org.sonatype.repository.helm.internal.content.recipe;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.cache.CacheController;
import org.sonatype.nexus.repository.content.facet.ContentProxyFacetSupport;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;
import org.sonatype.repository.helm.internal.AssetKind;
import org.sonatype.repository.helm.internal.content.HelmContentFacet;
import org.sonatype.repository.helm.internal.metadata.IndexYamlAbsoluteUrlRewriter;
import org.sonatype.repository.helm.internal.util.HelmPathUtils;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * @since 3.28
 */
@Named
public class HelmProxyFacet
    extends ContentProxyFacetSupport
{
  private final HelmPathUtils helmPathUtils;
  private final IndexYamlAbsoluteUrlRewriter indexYamlRewriter;

  private static final String INDEX_YAML = "/index.yaml";

  @Inject
  public HelmProxyFacet(final HelmPathUtils helmPathUtils,
                        final IndexYamlAbsoluteUrlRewriter indexYamlRewriter)
  {
    this.helmPathUtils = checkNotNull(helmPathUtils);
    this.indexYamlRewriter = checkNotNull(indexYamlRewriter);
  }

  @Nullable
  @Override
  protected   Content getCachedContent(final Context context) {
    Content content = content().getAsset(getAssetPath(context)).orElse(null);
    AssetKind assetKind = context.getAttributes().require(AssetKind.class);
    return assetKind == AssetKind.HELM_INDEX ? indexYamlRewriter.removeUrlsFromIndexYaml(content) : content;
  }

  @Nonnull
  @Override
  protected CacheController getCacheController(@Nonnull final Context context)
  {
    AssetKind assetKind = context.getAttributes().require(AssetKind.class);
    return cacheControllerHolder.require(assetKind.getCacheType());
  }

  @Override
  protected Content store(final Context context, final Content content) throws IOException {
    AssetKind assetKind = context.getAttributes().require(AssetKind.class);
    switch (assetKind) {
      case HELM_INDEX:
        return content().putIndex(getAssetPath(context), content, assetKind);
      case HELM_PACKAGE:
        return content().putComponent(getAssetPath(context), content, assetKind);
      default:
        throw new IllegalStateException("Received an invalid AssetKind of type: " + assetKind.name());
    }
  }

  private String getAssetPath(@Nonnull final Context context) {
    AssetKind assetKind = context.getAttributes().require(AssetKind.class);
    switch (assetKind) {
      case HELM_INDEX:
        return INDEX_YAML;
      case HELM_PACKAGE:
        TokenMatcher.State matcherState = helmPathUtils.matcherState(context);
        return helmPathUtils.contentFilePath(matcherState, true);
      default:
        throw new IllegalStateException("Received an invalid AssetKind of type: " + assetKind.name());
    }
  }

  @Override
  protected String getUrl(@Nonnull final Context context) {
    AssetKind assetKind = context.getAttributes().require(AssetKind.class);
    switch (assetKind) {
      case HELM_INDEX:
        return "index.yaml";
      case HELM_PACKAGE:
        TokenMatcher.State matcherState = helmPathUtils.matcherState(context);
        Optional<Content> indexOpt = content().getAsset(INDEX_YAML);
        if (!indexOpt.isPresent()) {
          log.info("Try to refetch index.yml file in repository: {}", getRepository().getName());
          indexOpt = fetchIndexYamlContext(context);
        }
        if (!indexOpt.isPresent()) {
          log.error("index.yml file is absent in repository: {}", getRepository().getName());
          return null;
        }
        String filename = helmPathUtils.filename(matcherState);
        return helmPathUtils.contentFileUrl(filename, indexOpt.get());
      default:
        throw new IllegalStateException("Received an invalid AssetKind of type: " + assetKind.name());
    }
  }

  private Optional<Content> fetchIndexYamlContext(@Nonnull final Context context)
  {
    Context indexYamlContext = buildContextForRepositoryIndexYaml(context);
    try {
      // fetch index.yaml file and return original metadata
      get(indexYamlContext);
      return content().getAsset(INDEX_YAML);
    }
    catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private Context buildContextForRepositoryIndexYaml(final Context contextPackage) {
    Repository repository = contextPackage.getRepository();

    Request request = new Request.Builder()
        .action(contextPackage.getRequest().getAction())
        .path(INDEX_YAML)
        .build();

    Context indexYamlContext = new Context(repository, request);
    indexYamlContext.getAttributes().backing().putAll(contextPackage.getAttributes().backing());
    indexYamlContext.getAttributes().set(AssetKind.class, AssetKind.HELM_INDEX);
    return indexYamlContext;
  }

  private HelmContentFacet content() {
    return getRepository().facet(HelmContentFacet.class);
  }
}
