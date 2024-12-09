package org.sonatype.nexus.repository.cocoapods.internal.proxy;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.RecipeSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.Type;
import org.sonatype.nexus.repository.attributes.AttributesFacet;
import org.sonatype.nexus.repository.cache.NegativeCacheFacet;
import org.sonatype.nexus.repository.cache.NegativeCacheHandler;
import org.sonatype.nexus.repository.cocoapods.CocoapodsFacet;
import org.sonatype.nexus.repository.cocoapods.internal.AssetKind;
import org.sonatype.nexus.repository.cocoapods.internal.CocoapodsFormat;
import org.sonatype.nexus.repository.cocoapods.internal.CocoapodsSecurityFacet;
import org.sonatype.nexus.repository.http.PartialFetchHandler;
import org.sonatype.nexus.repository.httpclient.HttpClientFacet;
import org.sonatype.nexus.repository.proxy.ProxyHandler;
import org.sonatype.nexus.repository.purge.PurgeUnusedFacet;
import org.sonatype.nexus.repository.routing.RoutingRuleHandler;
import org.sonatype.nexus.repository.search.SearchFacet;
import org.sonatype.nexus.repository.security.SecurityHandler;
import org.sonatype.nexus.repository.storage.SingleAssetComponentMaintenance;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.UnitOfWorkHandler;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.repository.view.ConfigurableViewFacet;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.Matcher;
import org.sonatype.nexus.repository.view.Route;
import org.sonatype.nexus.repository.view.Router;
import org.sonatype.nexus.repository.view.ViewFacet;
import org.sonatype.nexus.repository.view.handlers.ConditionalRequestHandler;
import org.sonatype.nexus.repository.view.handlers.ContentHeadersHandler;
import org.sonatype.nexus.repository.view.handlers.ExceptionHandler;
import org.sonatype.nexus.repository.view.handlers.FormatHighAvailabilitySupportHandler;
import org.sonatype.nexus.repository.view.handlers.HandlerContributor;
import org.sonatype.nexus.repository.view.handlers.HighAvailabilitySupportChecker;
import org.sonatype.nexus.repository.view.handlers.LastDownloadedHandler;
import org.sonatype.nexus.repository.view.handlers.TimingHandler;
import org.sonatype.nexus.repository.view.matchers.ActionMatcher;
import org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;

import static org.sonatype.nexus.repository.cocoapods.internal.AssetKind.CDN_METADATA;
import static org.sonatype.nexus.repository.cocoapods.internal.AssetKind.POD;
import static org.sonatype.nexus.repository.cocoapods.internal.AssetKind.SPEC;
import static org.sonatype.nexus.repository.cocoapods.internal.CocoapodsFormat.PACKAGE_NAME_KEY;
import static org.sonatype.nexus.repository.cocoapods.internal.CocoapodsFormat.PACKAGE_VERSION_KEY;
import static org.sonatype.nexus.repository.http.HttpHandlers.notFound;
import static org.sonatype.nexus.repository.http.HttpMethods.GET;
import static org.sonatype.nexus.repository.http.HttpMethods.HEAD;

/**
 * @since 3.19
 */
@Named(CocoapodsProxyRecipe.NAME)
@Singleton
public class CocoapodsProxyRecipe
    extends RecipeSupport
{
  public static final String NAME = "cocoapods-proxy";

  @Inject
  Provider<CocoapodsSecurityFacet> securityFacet;

  @Inject
  HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  @Inject
  Provider<ConfigurableViewFacet> viewFacet;

  @Inject
  Provider<HttpClientFacet> httpClientFacet;

  @Inject
  Provider<NegativeCacheFacet> negativeCacheFacet;

  @Inject
  Provider<CocoapodsProxyFacet> proxyFacet;

  @Inject
  Provider<CocoapodsFacet> cocoapodsFacet;

  @Inject
  Provider<StorageFacet> storageFacet;

  @Inject
  Provider<AttributesFacet> attributesFacet;

  @Inject
  Provider<SingleAssetComponentMaintenance> componentMaintenance;

  @Inject
  Provider<SearchFacet> searchFacet;

  @Inject
  Provider<PurgeUnusedFacet> purgeUnusedFacet;

  @Inject
  ExceptionHandler exceptionHandler;

  @Inject
  FormatHighAvailabilitySupportHandler highAvailabilitySupportHandler;

  @Inject
  RoutingRuleHandler routingHandler;

  @Inject
  TimingHandler timingHandler;

  @Inject
  SecurityHandler securityHandler;

  @Inject
  NegativeCacheHandler negativeCacheHandler;

  @Inject
  PartialFetchHandler partialFetchHandler;

  @Inject
  UnitOfWorkHandler unitOfWorkHandler;

  @Inject
  ProxyHandler proxyHandler;

  @Inject
  ConditionalRequestHandler conditionalRequestHandler;

  @Inject
  ContentHeadersHandler contentHeadersHandler;

  @Inject
  LastDownloadedHandler lastDownloadedHandler;

  @Inject
  HandlerContributor handlerContributor;

  @Inject
  public CocoapodsProxyRecipe(@Named(ProxyType.NAME) Type type, @Named(CocoapodsFormat.NAME) Format format) {
    super(type, format);
  }

  @Override
  public void apply(final Repository repository) throws Exception {
    repository.attach(securityFacet.get());
    repository.attach(configure(viewFacet.get()));
    repository.attach(httpClientFacet.get());
    repository.attach(negativeCacheFacet.get());
    repository.attach(proxyFacet.get());
    repository.attach(cocoapodsFacet.get());
    repository.attach(storageFacet.get());
    repository.attach(attributesFacet.get());
    repository.attach(componentMaintenance.get());
    repository.attach(searchFacet.get());
    repository.attach(purgeUnusedFacet.get());
  }

  @Override
  public boolean isFeatureEnabled() {
    return highAvailabilitySupportChecker.isSupported(getFormat().getValue());
  }

  private ViewFacet configure(final ConfigurableViewFacet facet) {
    Router.Builder builder = new Router.Builder();

    createRoutes(builder);

    builder.defaultHandlers(notFound());
    facet.configure(builder.create());
    return facet;
  }

  private void createRoutes(final Router.Builder builder) {
    createRoute(builder, createGetTokenMatcher("/Specs/{subtree:.*}"), SPEC);
    createRoute(builder, createGetTokenMatcher("{metadataPrefix:.*}/CocoaPods-version.yml"), CDN_METADATA);
    createRoute(builder, createGetTokenMatcher("{metadataPrefix:.*}/deprecated_podspecs.txt"), CDN_METADATA);
    createRoute(builder, createGetTokenMatcher("{metadataPrefix:.*}/all_pods{subtree:.*}.txt"), CDN_METADATA);
    createRoute(builder, createGetTokenMatcher(
        String.format("/pods/{%s}/{%s}/{filename}", PACKAGE_NAME_KEY, PACKAGE_VERSION_KEY)), POD);
  }

  private void createRoute(final Router.Builder builder,
                           final Matcher matcher,
                           final AssetKind assetKind)
  {

    Route.Builder route = new Route.Builder().matcher(matcher)
        .handler(timingHandler)
        .handler(assetKindHandler(assetKind));
    if (assetKind != CDN_METADATA && assetKind != SPEC) {
      //current implementation of cocoapods client does not support even basic auth for work with CDN.
      route.handler(securityHandler);
      route.handler(handlerContributor);
    }
    route.handler(highAvailabilitySupportHandler)
        .handler(routingHandler)
        .handler(exceptionHandler)
        .handler(negativeCacheHandler)
        .handler(conditionalRequestHandler)
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(unitOfWorkHandler)
        .handler(lastDownloadedHandler)
        .handler(proxyHandler);
    builder.route(route.create());
  }

  private static Matcher createGetTokenMatcher(String pattern) {
    return LogicMatchers.and(
        new ActionMatcher(HEAD, GET),
        new TokenMatcher(pattern));
  }

  private Handler assetKindHandler(final AssetKind assetKind) {
    return (context) -> {
      context.getAttributes().set(AssetKind.class, assetKind);
      return context.proceed();
    };
  }
}
