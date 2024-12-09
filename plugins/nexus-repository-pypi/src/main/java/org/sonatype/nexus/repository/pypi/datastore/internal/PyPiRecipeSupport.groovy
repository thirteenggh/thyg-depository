package org.sonatype.nexus.repository.pypi.datastore.internal

import javax.inject.Inject
import javax.inject.Provider

import org.sonatype.nexus.repository.pypi.datastore.PypiContentFacet
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.RecipeSupport
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.content.browse.BrowseFacet
import org.sonatype.nexus.repository.content.search.SearchFacet
import org.sonatype.nexus.repository.http.PartialFetchHandler
import org.sonatype.nexus.repository.pypi.internal.AssetKind
import org.sonatype.nexus.repository.pypi.internal.PyPiIndexFacet
import org.sonatype.nexus.repository.pypi.internal.PyPiSecurityFacet
import org.sonatype.nexus.repository.routing.RoutingRuleHandler
import org.sonatype.nexus.repository.security.SecurityHandler
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Context
import org.sonatype.nexus.repository.view.Route.Builder
import org.sonatype.nexus.repository.view.handlers.ConditionalRequestHandler
import org.sonatype.nexus.repository.view.handlers.ContentHeadersHandler
import org.sonatype.nexus.repository.view.handlers.ExceptionHandler
import org.sonatype.nexus.repository.view.handlers.HandlerContributor
import org.sonatype.nexus.repository.view.handlers.LastDownloadedHandler
import org.sonatype.nexus.repository.view.handlers.TimingHandler
import org.sonatype.nexus.repository.view.matchers.ActionMatcher
import org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher

import static org.sonatype.nexus.repository.http.HttpMethods.GET
import static org.sonatype.nexus.repository.http.HttpMethods.HEAD
import static org.sonatype.nexus.repository.http.HttpMethods.POST
/**
 * PyPI repository support.
 *
 * @since 3.29
 */
abstract class PyPiRecipeSupport
    extends RecipeSupport
{
  @Inject
  Provider<BrowseFacet> browseFacet

  @Inject
  Provider<PypiContentFacet> contentFacet

  @Inject
  Provider<PyPiSecurityFacet> securityFacet

  @Inject
  Provider<ConfigurableViewFacet> viewFacet

  @Inject
  Provider<SearchFacet> searchFacet

  @Inject
  Provider<PyPiLastAssetMaintenanceFacet> lastAssetMaintenanceFacet

  @Inject
  Provider<PyPiIndexFacet> indexFacet

  @Inject
  ExceptionHandler exceptionHandler

  @Inject
  TimingHandler timingHandler

  @Inject
  RoutingRuleHandler routingHandler

  @Inject
  SecurityHandler securityHandler

  @Inject
  PartialFetchHandler partialFetchHandler

  @Inject
  ConditionalRequestHandler conditionalRequestHandler

  @Inject
  ContentHeadersHandler contentHeadersHandler

  @Inject
  HandlerContributor handlerContributor

  @Inject
  LastDownloadedHandler lastDownloadedHandler

  protected PyPiRecipeSupport(final Type type, final Format format) {
    super(type, format)
  }

  /**
   * Sets a context attribute with asset kind for the route.
   */

  Closure assetKindHandler = { Context context, AssetKind value ->
    context.attributes.set(AssetKind, value)
    return context.proceed()
  }

  /**
   * Matcher for index mapping. Handles normalized naming based on PEP503 " the only valid characters in a name are the
   * ASCII alphabet, ASCII numbers, ., -, and _." https://www.python.org/dev/peps/pep-0503/#normalized-names
   */
  static Builder indexMatcher() {
    new Builder().matcher(
        LogicMatchers.and(
            new ActionMatcher(GET, HEAD),
            LogicMatchers.or(
                new TokenMatcher('/simple/{name:[0-9a-zA-z\\._-]+}'),
                new TokenMatcher('/simple/{name:[0-9a-zA-z\\._-]+}/')
            )
        ))
  }

  /**
   * Matcher for index mapping.
   */
  static Builder rootIndexMatcher() {
    new Builder().matcher(
        LogicMatchers.and(
            new ActionMatcher(GET, HEAD),
            new TokenMatcher('/simple/')
        ))
  }

  /**
   * Matcher for search mapping.
   */
  static Builder searchMatcher() {
    new Builder().matcher(
        LogicMatchers.and(
            new ActionMatcher(POST),
            new TokenMatcher('/pypi')
        ))
  }

  /**
   * Matcher for packages mapping.
   */
  static Builder packagesMatcher() {
    new Builder().matcher(
        LogicMatchers.and(
            new ActionMatcher(GET, HEAD),
            new TokenMatcher('{path:\\/packages\\/[^/].+\\/[^/]+\\/[^/]+}')
        ))
  }

  /**
   * Matcher for base mapping.
   */
  static Builder baseMatcher() {
    new Builder().matcher(
        LogicMatchers.and(
            new ActionMatcher(POST),
            new TokenMatcher('/')
        ))
  }
}
