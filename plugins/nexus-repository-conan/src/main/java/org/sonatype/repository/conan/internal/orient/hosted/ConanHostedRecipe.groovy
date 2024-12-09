package org.sonatype.repository.conan.internal.orient.hosted

import javax.annotation.Nonnull
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.common.property.SystemPropertiesHelper
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.types.HostedType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Router
import org.sonatype.nexus.repository.view.ViewFacet
import org.sonatype.nexus.repository.view.handlers.HighAvailabilitySupportChecker
import org.sonatype.repository.conan.internal.ConanFormat
import org.sonatype.repository.conan.internal.ConanRecipeSupport
import org.sonatype.repository.conan.internal.ConanSystemProperties
import org.sonatype.repository.conan.internal.orient.hosted.v1.ConanHostedApiV1
import org.sonatype.repository.conan.internal.orient.hosted.v1.ConanHostedFacet
import org.sonatype.repository.conan.internal.security.token.ConanTokenFacet

import com.google.inject.Provider

import static org.sonatype.nexus.repository.http.HttpHandlers.notFound

/**
 * @since 3.28
 */
@Named(ConanHostedRecipe.NAME)
@Singleton
class ConanHostedRecipe
    extends ConanRecipeSupport
{
  public static final String NAME = 'conan-hosted'

  @Inject
  Provider<ConanHostedFacet> hostedFacet

  @Inject
  Provider<ConanTokenFacet> tokenFacet

  @Inject
  HighAvailabilitySupportChecker highAvailabilitySupportChecker

  private ConanHostedApiV1 apiV1

  @Inject
  protected ConanHostedRecipe(@Named(HostedType.NAME) final Type type,
                              @Named(ConanFormat.NAME) final Format format,
                              final ConanHostedApiV1 apiV1)
  {
    super(type, format)
    this.apiV1 = apiV1
  }

  @Override
  void apply(@Nonnull final Repository repository) throws Exception {
    repository.attach(securityFacet.get())
    repository.attach(configure(viewFacet.get()))
    repository.attach(httpClientFacet.get())
    repository.attach(componentMaintenanceFacet.get())
    repository.attach(tokenFacet.get())
    repository.attach(hostedFacet.get())
    repository.attach(storageFacet.get())
    repository.attach(attributesFacet.get())
    repository.attach(searchFacet.get())
  }

  ViewFacet configure(final ConfigurableViewFacet facet) {
    Router.Builder builder = new Router.Builder()

    addBrowseUnsupportedRoute(builder)

    apiV1.create(builder)

    builder.defaultHandlers(notFound())
    facet.configure(builder.create())
    return facet
  }

  @Override
  boolean isFeatureEnabled() {
    highAvailabilitySupportChecker.isSupported(getFormat().getValue()) &&
        SystemPropertiesHelper.getBoolean(ConanSystemProperties.HOSTED_ENABLED_PROPERTY, false)
  }
}
