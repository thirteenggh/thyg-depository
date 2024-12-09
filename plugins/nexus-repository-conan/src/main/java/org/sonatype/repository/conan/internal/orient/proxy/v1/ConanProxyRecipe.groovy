package org.sonatype.repository.conan.internal.orient.proxy.v1

import javax.annotation.Nonnull
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.proxy.ProxyHandler
import org.sonatype.nexus.repository.types.ProxyType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Router
import org.sonatype.nexus.repository.view.ViewFacet
import org.sonatype.nexus.repository.view.handlers.HighAvailabilitySupportChecker
import org.sonatype.repository.conan.internal.ConanFormat
import org.sonatype.repository.conan.internal.ConanRecipeSupport
import org.sonatype.repository.conan.internal.security.token.ConanTokenFacet

import com.google.inject.Provider

import static org.sonatype.nexus.repository.http.HttpHandlers.notFound

/**
 * @since 3.28
 */
@Named(ConanProxyRecipe.NAME)
@Singleton
class ConanProxyRecipe
  extends ConanRecipeSupport
{
  public static final String NAME = 'conan-proxy'

  @Inject
  Provider<ConanProxyFacet> proxyFacet

  @Inject
  HighAvailabilitySupportChecker highAvailabilitySupportChecker

  @Inject
  ProxyHandler proxyHandler

  @Inject
  Provider<ConanTokenFacet> tokenFacet

  private ConanProxyApiV1 conanApiV1

  @Inject
  ConanProxyRecipe(@Named(ProxyType.NAME) final Type type,
                   @Named(ConanFormat.NAME) final Format format,
                   final ConanProxyApiV1 conanApiV1) {
    super(type, format)
    this.conanApiV1 = conanApiV1
  }

  @Override
  boolean isFeatureEnabled() {
    return highAvailabilitySupportChecker.isSupported(getFormat().getValue())
  }

  @Override
  void apply(@Nonnull final Repository repository) throws Exception {
    repository.attach(securityFacet.get())
    repository.attach(configure(viewFacet.get()))
    repository.attach(httpClientFacet.get())
    repository.attach(tokenFacet.get())
    repository.attach(negativeCacheFacet.get())
    repository.attach(componentMaintenanceFacet.get())
    repository.attach(proxyFacet.get())
    repository.attach(storageFacet.get())
    repository.attach(attributesFacet.get())
    repository.attach(searchFacet.get())
    repository.attach(purgeUnusedFacet.get())
  }

  ViewFacet configure(ConfigurableViewFacet facet) {
    Router.Builder builder = new Router.Builder()

    addBrowseUnsupportedRoute(builder)

    conanApiV1.create(builder);

    builder.defaultHandlers(notFound())
    facet.configure(builder.create())
    return facet
  }
}
