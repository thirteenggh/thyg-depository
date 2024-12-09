package org.sonatype.nexus.repository.r.internal.group

import javax.annotation.Nonnull
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.group.GroupFacetImpl
import org.sonatype.nexus.repository.group.GroupHandler
import org.sonatype.nexus.repository.http.HttpHandlers
import org.sonatype.nexus.repository.r.internal.AssetKind
import org.sonatype.nexus.repository.r.internal.RCommonHandlers
import org.sonatype.nexus.repository.r.internal.RFormat
import org.sonatype.nexus.repository.r.internal.RRecipeSupport
import org.sonatype.nexus.repository.types.GroupType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Router.Builder
import org.sonatype.nexus.repository.view.ViewFacet

/**
 * R group repository recipe.
 * @since 3.28
 */
@Named(RGroupRecipe.NAME)
@Singleton
class RGroupRecipe
    extends RRecipeSupport
{
  public static final String NAME = 'r-group'

  @Inject
  Provider<GroupFacetImpl> groupFacet

  @Inject
  PackagesGroupHandler packagesGroupHandler

  @Inject
  GroupHandler standardGroupHandler

  @Inject
  RCommonHandlers commonHandlers;

  @Inject
  RGroupRecipe(@Named(GroupType.NAME) final Type type, @Named(RFormat.NAME) final Format format) {
    super(type, format)
  }

  @Override
  void apply(@Nonnull final Repository repository) throws Exception {
    repository.attach(groupFacet.get())
    repository.attach(storageFacet.get())
    repository.attach(securityFacet.get())
    repository.attach(configure(viewFacet.get()))
    repository.attach(attributesFacet.get())
  }

  /**
   * Configure {@link ViewFacet}.
   */
  private ViewFacet configure(final ConfigurableViewFacet facet) {
    Builder builder = new Builder()

    builder.route(metadataPackagesRdsMatcher()
        .handler(securityHandler)
        .handler(commonHandlers.notSupportedMetadataRequest)
        .create())

    builder.route(packagesMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.PACKAGES))
        .handler(securityHandler)
        .handler(highAvailabilitySupportHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(packagesGroupHandler)
        .create())

    builder.route(metadataRdsMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.RDS_METADATA))
        .handler(securityHandler)
        .handler(highAvailabilitySupportHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(packagesGroupHandler)
        .create())

    builder.route(archiveMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.ARCHIVE))
        .handler(securityHandler)
        .handler(highAvailabilitySupportHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(standardGroupHandler)
        .create())

    addBrowseUnsupportedRoute(builder)

    builder.defaultHandlers(HttpHandlers.notFound())

    facet.configure(builder.create())

    return facet
  }
}
