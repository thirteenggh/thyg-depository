package org.sonatype.nexus.content.maven.internal.recipe

import javax.annotation.Nonnull
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

import org.sonatype.nexus.content.maven.internal.index.MavenContentGroupIndexFacet
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.group.GroupHandler
import org.sonatype.nexus.repository.http.HttpMethods
import org.sonatype.nexus.repository.maven.internal.Maven2Format
import org.sonatype.nexus.repository.maven.internal.group.MavenGroupFacet
import org.sonatype.nexus.repository.maven.internal.group.MergingGroupHandler
import org.sonatype.nexus.repository.maven.internal.matcher.MavenArchetypeCatalogMatcher
import org.sonatype.nexus.repository.maven.internal.matcher.MavenIndexMatcher
import org.sonatype.nexus.repository.maven.internal.matcher.MavenPathMatcher
import org.sonatype.nexus.repository.maven.internal.matcher.MavenRepositoryMetadataMatcher
import org.sonatype.nexus.repository.maven.internal.recipes.Maven2GroupRecipe
import org.sonatype.nexus.repository.types.GroupType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Route.Builder
import org.sonatype.nexus.repository.view.Router
import org.sonatype.nexus.repository.view.ViewFacet
import org.sonatype.nexus.repository.view.matchers.ActionMatcher
import org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers

import static org.sonatype.nexus.repository.http.HttpHandlers.notFound

/**
 * @since 3.25
 */
@Named(Maven2GroupRecipe.NAME)
@Singleton
class MavenGroupRecipe
    extends MavenRecipeSupport
    implements Maven2GroupRecipe
{

  @Inject
  Provider<MavenContentGroupIndexFacet> mavenGroupIndexFacet

  @Inject
  Provider<MavenGroupFacet> mavenGroupFacet

  @Inject
  GroupHandler groupHandler

  @Inject
  MergingGroupHandler mergingGroupHandler

  @Inject
  MavenContentIndexGroupHandler indexGroupHandler

  @Inject
  MavenGroupRecipe(
      @Named(GroupType.NAME) final Type type,
      @Named(Maven2Format.NAME) final Format format)
  {
    super(type, format)
  }

  @Override
  void apply(@Nonnull final Repository repository) throws Exception {
    repository.attach(securityFacet.get())
    repository.attach(mavenGroupFacet.get())
    repository.attach(mavenContentFacet.get())
    repository.attach(mavenGroupIndexFacet.get())
    repository.attach(configure(viewFacet.get()))
    repository.attach(mavenMaintenanceFacet.get())
  }

  private ViewFacet configure(final ConfigurableViewFacet facet) {
    Router.Builder builder = new Router.Builder()

    addBrowseUnsupportedRoute(builder)

    // Note: partialFetchHandler NOT added for Maven metadata
    builder.route(newMetadataRouteBuilder()
        .handler(contentHeadersHandler)
        .handler(mergingGroupHandler)
        .create())

    builder.route(newIndexRouteBuilder()
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(indexGroupHandler)
        .create())

    builder.route(newArchetypeCatalogRouteBuilder()
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(mergingGroupHandler)
        .create())

    builder.route(newMavenPathRouteBuilder()
        .handler(partialFetchHandler)
        .handler(groupHandler)
        .create())

    builder.defaultHandlers(notFound())

    facet.configure(builder.create())

    return facet
  }

  Builder newMavenPathRouteBuilder() {
    return new Builder()
        .matcher(new MavenPathMatcher(mavenPathParser))
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(routingHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(conditionalRequestHandler)
  }

  Builder newMetadataRouteBuilder() {
    return new Builder()
        .matcher(new MavenRepositoryMetadataMatcher(mavenPathParser))
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(routingHandler)
        .handler(exceptionHandler)
        .handler(conditionalRequestHandler)
  }

  /**
   * Only GET, HEAD actions allowed, as nothing publishes the binary index, only consumes.
   */
  Builder newIndexRouteBuilder() {
    return new Builder()
        .matcher(
            LogicMatchers.and(
                new MavenIndexMatcher(mavenPathParser),
                LogicMatchers.or(
                    new ActionMatcher(HttpMethods.GET),
                    new ActionMatcher(HttpMethods.HEAD)
                )
            )
        )
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(routingHandler)
        .handler(exceptionHandler)
        .handler(conditionalRequestHandler)
  }

  Builder newArchetypeCatalogRouteBuilder() {
    return new Builder()
        .matcher(new MavenArchetypeCatalogMatcher(mavenPathParser))
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(routingHandler)
        .handler(exceptionHandler)
        .handler(conditionalRequestHandler)
  }
}
