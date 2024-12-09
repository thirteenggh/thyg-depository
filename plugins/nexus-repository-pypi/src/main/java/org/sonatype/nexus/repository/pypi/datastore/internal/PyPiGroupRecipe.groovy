package org.sonatype.nexus.repository.pypi.datastore.internal

import javax.annotation.Nonnull
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.group.GroupHandler
import org.sonatype.nexus.repository.http.HttpHandlers
import org.sonatype.nexus.repository.pypi.internal.AssetKind
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat
import org.sonatype.nexus.repository.pypi.internal.SearchGroupHandler
import org.sonatype.nexus.repository.types.GroupType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Router
import org.sonatype.nexus.repository.view.ViewFacet

/**
 * PyPI group repository recipe.
 *
 * @since 3.29
 */
@Named(PyPiGroupRecipe.NAME)
@Singleton
class PyPiGroupRecipe
    extends PyPiRecipeSupport
{
  public static final String NAME = 'pypi-group'

  @Inject
  Provider<PyPiGroupFacet> groupFacet

  @Inject
  GroupHandler standardGroupHandler

  @Inject
  PyPiIndexGroupHandler indexGroupHandler

  @Inject
  SearchGroupHandler searchGroupHandler

  @Inject
  PyPiGroupRecipe(@Named(GroupType.NAME) final Type type, @Named(PyPiFormat.NAME) final Format format) {
    super(type, format)
  }

  @Override
  void apply(@Nonnull final Repository repository) throws Exception {
    repository.attach(configure(viewFacet.get()))
    repository.attach(securityFacet.get())
    repository.attach(contentFacet.get())
    repository.attach(groupFacet.get())
    repository.attach(searchFacet.get())
    repository.attach(browseFacet.get())
  }

  /**
   * Configure {@link ViewFacet}.
   */
  private ViewFacet configure(final ConfigurableViewFacet facet) {
    Router.Builder builder = new Router.Builder()

    builder.route(rootIndexMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.ROOT_INDEX))
        .handler(securityHandler)
        .handler(exceptionHandler)
        .handler(conditionalRequestHandler)
        .handler(contentHeadersHandler)
        .handler(lastDownloadedHandler)
        .handler(indexGroupHandler)
        .create())

    builder.route(indexMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.INDEX))
        .handler(securityHandler)
        .handler(exceptionHandler)
        .handler(conditionalRequestHandler)
        .handler(contentHeadersHandler)
        .handler(lastDownloadedHandler)
        .handler(indexGroupHandler)
        .create())

    builder.route(packagesMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.PACKAGE))
        .handler(securityHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(conditionalRequestHandler)
        .handler(standardGroupHandler)
        .create())

    builder.route(searchMatcher()
        .handler(timingHandler)
        .handler(assetKindHandler.rcurry(AssetKind.SEARCH))
        .handler(securityHandler)
        .handler(exceptionHandler)
        .handler(searchGroupHandler)
        .create())

    addBrowseUnsupportedRoute(builder)

    builder.defaultHandlers(HttpHandlers.notFound())

    facet.configure(builder.create())

    return facet
  }
}
