package org.sonatype.nexus.repository.npm.internal.orient

import javax.annotation.Nonnull
import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

import org.sonatype.nexus.repository.Facet
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.group.GroupFacet
import org.sonatype.nexus.repository.group.GroupHandler
import org.sonatype.nexus.repository.http.HttpHandlers
import org.sonatype.nexus.repository.npm.internal.NpmFormat
import org.sonatype.nexus.repository.npm.internal.NpmGroupAuditHandler
import org.sonatype.nexus.repository.npm.internal.NpmGroupAuditQuickHandler
import org.sonatype.nexus.repository.npm.internal.NpmGroupWriteHandler
import org.sonatype.nexus.repository.npm.internal.NpmHandlers
import org.sonatype.nexus.repository.npm.internal.NpmPingHandler
import org.sonatype.nexus.repository.npm.internal.NpmWhoamiHandler
import org.sonatype.nexus.repository.npm.internal.search.v1.NpmSearchGroupHandler
import org.sonatype.nexus.repository.npm.orient.internal.search.legacy.NpmSearchIndexFacetGroup
import org.sonatype.nexus.repository.types.GroupType
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Handler
import org.sonatype.nexus.repository.view.Router
import org.sonatype.nexus.repository.view.handlers.ContentHeadersHandler

import static org.sonatype.nexus.repository.http.HttpMethods.GET
import static org.sonatype.nexus.repository.http.HttpMethods.HEAD
import static org.sonatype.nexus.repository.http.HttpMethods.PUT
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.auditMatcher
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.auditQuickMatcher
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.distTagsMatcher
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.packageMatcher
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.pingMatcher
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.searchIndexMatcher
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.searchV1Matcher
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.tarballMatcher
import static org.sonatype.nexus.repository.npm.internal.NpmPaths.whoamiMatcher

/**
 * npm group repository recipe.
 *
 * @since 3.0
 */
@Named(OrientNpmGroupRecipe.NAME)
@Singleton
class OrientNpmGroupRecipe
    extends OrientNpmRecipeSupport
{
  public static final String NAME = 'npm-group'

  @Inject
  Provider<OrientNpmGroupDataFacet> npmGroupFacet

  @Inject
  Provider<NpmSearchIndexFacetGroup> npmSearchIndexFacet

  @Inject
  OrientNpmGroupPackageHandler packageHandler

  @Inject
  OrientNpmGroupDistTagsHandler distTagsHandler

  @Inject
  Provider<GroupFacet> groupFacet

  @Inject
  @Named('groupWriteFacet')
  Provider<GroupFacet> writeableGroupFacet

  @Inject
  NpmGroupWriteHandler groupWriteHandler

  @Inject
  GroupHandler tarballHandler

  @Inject
  NpmSearchGroupHandler searchHandler

  @Inject
  ContentHeadersHandler contentHeadersHandler

  @Inject
  NpmWhoamiHandler npmWhoamiHandler

  @Inject
  NpmPingHandler pingHandler

  @Inject
  NpmGroupAuditHandler npmGroupAuditHandler

  @Inject
  NpmGroupAuditQuickHandler npmGroupAuditQuickHandler

  @Inject
  @Named("nexus.analytics.npmGroupDeployHandler")
  @Nullable
  Handler npmGroupDeployAnalyticsHandler

  @Inject
  OrientNpmGroupRecipe(@Named(GroupType.NAME) final Type type,
                       @Named(NpmFormat.NAME) final Format format)
  {
    super(type, format)
  }

  @Override
  void apply(@Nonnull final Repository repository) throws Exception {
    repository.attach(writeableGroupFacet.get() ?: groupFacet.get())
    repository.attach(npmGroupFacet.get())
    repository.attach(storageFacet.get())
    repository.attach(attributesFacet.get())
    repository.attach(securityFacet.get())
    repository.attach(tokenFacet.get())
    repository.attach(npmSearchIndexFacet.get())
    repository.attach(npmFacet.get())
    repository.attach(npmAuditFacetProvider.get())
    repository.attach(npmAuditTarballFacetProvider.get())
    repository.attach(configure(viewFacet.get()))
  }

  Facet configure(final ConfigurableViewFacet facet) {
    Router.Builder builder = new Router.Builder()

    addBrowseUnsupportedRoute(builder)

    // GET /-/all (npm search)
    builder.route(searchIndexMatcher()
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(unitOfWorkHandler)
        .handler(NpmHandlers.searchIndex)
        .create())

    // GET /-/v1/search (npm v1 search)
    builder.route(searchV1Matcher()
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(unitOfWorkHandler)
        .handler(searchHandler)
        .create())

    // GET /-/whoami
    builder.route(whoamiMatcher()
        .handler(timingHandler)
        .handler(npmWhoamiHandler)
        .create())

    // GET /-/ping
    builder.route(pingMatcher()
        .handler(timingHandler)
        .handler(pingHandler)
        .create())

    // POST /-/npm/v1/security/audits
    builder.route(auditMatcher()
        .handler(auditAnalyticsHandler ?: { context -> context.proceed() } as Handler)
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(unitOfWorkHandler)
        .handler(auditErrorHandler)
        .handler(npmGroupAuditHandler)
        .create())

    // POST /-/npm/v1/security/audits/quick
    builder.route(auditQuickMatcher()
        .handler(timingHandler)
        .handler(unitOfWorkHandler)
        .handler(auditErrorHandler)
        .handler(npmGroupAuditQuickHandler)
        .create())

    // GET /packageName (npm install)
    builder.route(packageMatcher(GET, HEAD)
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(unitOfWorkHandler)
        .handler(conditionalRequestHandler)
        .handler(contentHeadersHandler)
        .handler(lastDownloadedHandler)
        .handler(packageHandler)
        .create())

    // GET /packageName/-/tarballName (npm install)
    builder.route(tarballMatcher(GET, HEAD)
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(handlerContributor)
        .handler(tarballHandler)
        .create())

    // GET /-/package/packageName/dist-tags (npm dist-tag ls pkg)
    builder.route(distTagsMatcher(GET)
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(unitOfWorkHandler)
        .handler(lastDownloadedHandler)
        .handler(distTagsHandler)
        .create())

    // PUT /packageName (npm publish)
    builder.route(packageMatcher(PUT)
        .handler(npmGroupDeployAnalyticsHandler ?: { context -> context.proceed() } as Handler)
        .handler(timingHandler)
        .handler(securityHandler)
        .handler(NpmHandlers.npmErrorHandler)
        .handler(handlerContributor)
        .handler(conditionalRequestHandler)
        .handler(contentHeadersHandler)
        .handler(unitOfWorkHandler)
        .handler(groupWriteHandler)
        .create())

    createUserRoutes(builder)

    builder.defaultHandlers(HttpHandlers.badRequest())

    facet.configure(builder.create())

    return facet
  }
}
