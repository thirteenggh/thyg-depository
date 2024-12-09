package org.sonatype.nexus.orient.raw.internal

import javax.annotation.Nonnull
import javax.annotation.Priority
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

import org.sonatype.nexus.content.raw.internal.recipe.ContentDispositionHandler
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.RecipeSupport
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.attributes.AttributesFacet
import org.sonatype.nexus.repository.group.GroupFacet
import org.sonatype.nexus.repository.group.GroupHandler
import org.sonatype.nexus.repository.http.HttpHandlers
import org.sonatype.nexus.repository.raw.internal.RawFormat
import org.sonatype.nexus.repository.raw.internal.RawSecurityFacet
import org.sonatype.nexus.repository.security.SecurityHandler
import org.sonatype.nexus.repository.storage.StorageFacet
import org.sonatype.nexus.repository.types.GroupType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.handlers.ExceptionHandler
import org.sonatype.nexus.repository.view.Route
import org.sonatype.nexus.repository.view.Router
import org.sonatype.nexus.repository.view.ViewFacet
import org.sonatype.nexus.repository.view.handlers.HandlerContributor
import org.sonatype.nexus.repository.view.handlers.TimingHandler
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher

/**
 * Raw group repository recipe.
 *
 * @since 3.0
 */
@Named(RawGroupRecipe.NAME)
@Priority(Integer.MAX_VALUE)
@Singleton
class RawGroupRecipe
    extends RecipeSupport
{
  public static final String NAME = 'raw-group'

  @Inject
  Provider<StorageFacet> storageFacet

  @Inject
  Provider<RawSecurityFacet> securityFacet

  @Inject
  Provider<ConfigurableViewFacet> viewFacet

  @Inject
  Provider<AttributesFacet> attributesFacet

  @Inject
  Provider<GroupFacet> groupFacet

  @Inject
  ExceptionHandler exceptionHandler

  @Inject
  TimingHandler timingHandler

  @Inject
  SecurityHandler securityHandler

  @Inject
  GroupHandler groupHandler

  @Inject
  HandlerContributor handlerContributor

  @Inject
  ContentDispositionHandler contentDispositionHandler

  @Inject
  RawGroupRecipe(@Named(GroupType.NAME) Type type,
                 @Named(RawFormat.NAME) Format format)
  {
    super(type, format)
  }

  @Override
  void apply(@Nonnull final Repository repository) throws Exception {
    repository.attach(storageFacet.get())
    repository.attach(securityFacet.get())
    repository.attach(attributesFacet.get())
    repository.attach(configure(viewFacet.get()))
    repository.attach(groupFacet.get())
  }

  /**
   * Configure {@link ViewFacet}.
   */
  private ViewFacet configure(final ConfigurableViewFacet viewFacet) {
    Router.Builder builder = new Router.Builder()

    addBrowseUnsupportedRoute(builder)

    builder.route(new Route.Builder()
        .matcher(new TokenMatcher('/{name:.+}'))
        .handler(timingHandler)
        .handler(contentDispositionHandler)
        .handler(securityHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(groupHandler)
        .create())

    builder.defaultHandlers(HttpHandlers.badRequest())

    viewFacet.configure(builder.create())

    return viewFacet
  }
}
