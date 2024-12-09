package org.sonatype.nexus.repository.pypi.datastore.internal

import javax.annotation.Nonnull
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

import org.sonatype.nexus.repository.pypi.datastore.PypiContentFacet
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.cache.NegativeCacheFacet
import org.sonatype.nexus.repository.cache.NegativeCacheHandler
import org.sonatype.nexus.repository.http.HttpHandlers
import org.sonatype.nexus.repository.httpclient.HttpClientFacet
import org.sonatype.nexus.repository.purge.PurgeUnusedFacet
import org.sonatype.nexus.repository.pypi.internal.AssetKind
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat
import org.sonatype.nexus.repository.pypi.internal.PyPiProxyHandler
import org.sonatype.nexus.repository.types.ProxyType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Router.Builder
import org.sonatype.nexus.repository.view.ViewFacet
/**
 * PyPI proxy repository recipe.
 *
 * @since 3.29
 */
@Named(PyPiProxyRecipe.NAME)
@Singleton
class PyPiProxyRecipe
    extends PyPiRecipeSupport
{
  public static final String NAME = 'pypi-proxy'

  @Inject
  Provider<PyPiProxyFacet> proxyFacet

  @Inject
  Provider<PypiContentFacet> contentFacet

  @Inject
  Provider<HttpClientFacet> httpClientFacet

  @Inject
  Provider<NegativeCacheFacet> negativeCacheFacet

  @Inject
  NegativeCacheHandler negativeCacheHandler

  @Inject
  Provider<PurgeUnusedFacet> purgeUnusedFacet

  @Inject
  PyPiProxyHandler proxyHandler

  @Inject
  PyPiProxyRecipe(@Named(ProxyType.NAME) final Type type, @Named(PyPiFormat.NAME) final Format format) {
    super(type, format)
  }

  @Override
  void apply(@Nonnull final Repository repository) throws Exception {
    repository.attach(securityFacet.get())
    repository.attach(configure(viewFacet.get()))
    repository.attach(httpClientFacet.get())
    repository.attach(negativeCacheFacet.get())
    repository.attach(proxyFacet.get())
    repository.attach(contentFacet.get())
    repository.attach(searchFacet.get())
    repository.attach(purgeUnusedFacet.get())
    repository.attach(browseFacet.get())
    repository.attach(lastAssetMaintenanceFacet.get())
    repository.attach(indexFacet.get())
  }

  /**
   * Configure {@link ViewFacet}.
   */
  private ViewFacet configure(final ConfigurableViewFacet facet) {
    Builder builder = new Builder()

    builder.route(indexMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.INDEX))
        .handler(securityHandler)
        .handler(routingHandler)
        .handler(exceptionHandler)
        .handler(negativeCacheHandler)
        .handler(conditionalRequestHandler)
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(lastDownloadedHandler)
        .handler(proxyHandler)
        .create())

    builder.route(rootIndexMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.ROOT_INDEX))
        .handler(securityHandler)
        .handler(routingHandler)
        .handler(exceptionHandler)
        .handler(negativeCacheHandler)
        .handler(conditionalRequestHandler)
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(lastDownloadedHandler)
        .handler(proxyHandler)
        .create())

    builder.route(packagesMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.PACKAGE))
        .handler(securityHandler)
        .handler(routingHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(negativeCacheHandler)
        .handler(conditionalRequestHandler)
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(lastDownloadedHandler)
        .handler(proxyHandler)
        .create())

    builder.route(searchMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.SEARCH))
        .handler(securityHandler)
        .handler(exceptionHandler)
        .handler(negativeCacheHandler)
        .handler(conditionalRequestHandler)
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(proxyHandler)
        .create())

    addBrowseUnsupportedRoute(builder)

    builder.defaultHandlers(HttpHandlers.notFound())

    facet.configure(builder.create())

    return facet
  }

}
