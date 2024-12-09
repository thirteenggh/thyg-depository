package org.sonatype.repository.helm.internal.orient.hosted

import javax.annotation.Nonnull
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.http.HttpHandlers
import org.sonatype.nexus.repository.types.HostedType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Context
import org.sonatype.nexus.repository.view.Matcher
import org.sonatype.nexus.repository.view.Route
import org.sonatype.nexus.repository.view.Router
import org.sonatype.nexus.repository.view.ViewFacet
import org.sonatype.nexus.repository.view.matchers.ActionMatcher
import org.sonatype.nexus.repository.view.matchers.LiteralMatcher
import org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher
import org.sonatype.repository.helm.internal.AssetKind
import org.sonatype.repository.helm.internal.HelmFormat
import org.sonatype.repository.helm.internal.HelmRecipeSupport
import org.sonatype.repository.helm.internal.orient.createindex.CreateIndexFacetImpl

import static org.sonatype.nexus.repository.http.HttpMethods.DELETE
import static org.sonatype.nexus.repository.http.HttpMethods.PUT
import static org.sonatype.nexus.repository.http.HttpMethods.POST
import static org.sonatype.repository.helm.internal.AssetKind.HELM_PACKAGE
import static org.sonatype.repository.helm.internal.AssetKind.HELM_PROVENANCE

/**
 * Helm Hosted Recipe
 *
 * @since 3.28
 */
@Named(HelmHostedRecipe.NAME)
@Singleton
class HelmHostedRecipe
    extends HelmRecipeSupport
{
  public static final String NAME = 'helm-hosted'

  @Inject
  HostedHandlers hostedHandlers

  @Inject
  Provider<CreateIndexFacetImpl> createIndexFacet

  @Inject
  Provider<HelmHostedFacetImpl> hostedFacet

  @Inject
  HelmHostedRecipe(@Named(HostedType.NAME) final Type type, @Named(HelmFormat.NAME) final Format format) {
    super(type, format)
  }

  @Override
  void apply(@Nonnull final Repository repository) throws Exception {
    repository.attach(securityFacet.get())
    repository.attach(configure(viewFacet.get()))
    repository.attach(httpClientFacet.get())
    repository.attach(componentMaintenanceFacet.get())
    repository.attach(helmFacet.get())
    repository.attach(helmRestoreFacet.get())
    repository.attach(storageFacet.get())
    repository.attach(hostedFacet.get())
    repository.attach(createIndexFacet.get())
    repository.attach(searchFacet.get())
    repository.attach(attributesFacet.get())
  }

  /**
   * Configure {@link ViewFacet}.
   */
  private ViewFacet configure(final ConfigurableViewFacet facet) {
    Router.Builder builder = new Router.Builder()

    addBrowseUnsupportedRoute(builder)

    [indexMatcher(), packageMatcher()].each { matcher ->
      builder.route(new Route.Builder().matcher(matcher)
          .handler(timingHandler)
          .handler(securityHandler)
          .handler(formatHighAvailabilitySupportHandler)
          .handler(exceptionHandler)
          .handler(handlerContributor)
          .handler(partialFetchHandler)
          .handler(contentHeadersHandler)
          .handler(unitOfWorkHandler)
          .handler(hostedHandlers.get)
          .create())
    }

    [chartUploadMatcher(), provenanceUploadMatcher()].each { matcher ->
      builder.route(new Route.Builder().matcher(matcher)
          .handler(timingHandler)
          .handler(securityHandler)
          .handler(formatHighAvailabilitySupportHandler)
          .handler(exceptionHandler)
          .handler(handlerContributor)
          .handler(conditionalRequestHandler)
          .handler(partialFetchHandler)
          .handler(contentHeadersHandler)
          .handler(unitOfWorkHandler)
          .handler(hostedHandlers.upload)
          .create())
    }

    builder.route(new Route.Builder().matcher(chartPushMatcher())
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(formatHighAvailabilitySupportHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(conditionalRequestHandler)
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(unitOfWorkHandler)
        .handler(hostedHandlers.push)
        .create())

    builder.route(new Route.Builder().matcher(chartDeleteMatcher())
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(formatHighAvailabilitySupportHandler)
        .handler(exceptionHandler)
        .handler(handlerContributor)
        .handler(conditionalRequestHandler)
        .handler(partialFetchHandler)
        .handler(contentHeadersHandler)
        .handler(unitOfWorkHandler)
        .handler(hostedHandlers.delete)
        .create())

    builder.defaultHandlers(HttpHandlers.notFound())

    facet.configure(builder.create())

    return facet
  }

  static Matcher chartPushMatcher() {
    LogicMatchers.and(
        new ActionMatcher(POST),
        new LiteralMatcher('/api/charts')
    )
  }

  static Matcher chartUploadMatcher() {
    chartMethodMatcher(PUT)
  }

  static Matcher provenanceUploadMatcher() {
    provenanceMethodMatcher(PUT)
  }

  static Matcher chartDeleteMatcher() {
    chartMethodMatcher(DELETE)
  }

  static Matcher chartMethodMatcher(final String... httpMethods) {
    LogicMatchers.and(
        new ActionMatcher(httpMethods),
        tokenMatcherForExtensionAndName('tgz'),
        new Matcher() {
          @Override
          boolean matches(final Context context) {
            context.attributes.set(AssetKind.class, HELM_PACKAGE)
            return true
          }
        }
    )
  }

  static Matcher provenanceMethodMatcher(final String... httpMethods) {
    LogicMatchers.and(
        new ActionMatcher(httpMethods),
        tokenMatcherForExtensionAndName('tgz.prov'),
        new Matcher() {
          @Override
          boolean matches(final Context context) {
            context.attributes.set(AssetKind.class, HELM_PROVENANCE)
            return true
          }
        }
    )
  }

  static TokenMatcher tokenMatcherForExtensionAndName(final String extension, final String filename = '.+') {
    new TokenMatcher("/{filename:${filename}}.{extension:${extension}}")
  }
}
