package org.sonatype.nexus.repository.r.internal

import javax.inject.Inject
import javax.inject.Provider

import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.RecipeSupport
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.attributes.AttributesFacet
import org.sonatype.nexus.repository.http.PartialFetchHandler
import org.sonatype.nexus.repository.httpclient.HttpClientFacet
import org.sonatype.nexus.repository.r.RFacet
import org.sonatype.nexus.repository.r.RRestoreFacet
import org.sonatype.nexus.repository.r.internal.security.RSecurityFacet
import org.sonatype.nexus.repository.routing.RoutingRuleHandler
import org.sonatype.nexus.repository.search.SearchFacet
import org.sonatype.nexus.repository.security.SecurityHandler
import org.sonatype.nexus.repository.storage.StorageFacet
import org.sonatype.nexus.repository.storage.UnitOfWorkHandler
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.Context
import org.sonatype.nexus.repository.view.Matcher
import org.sonatype.nexus.repository.view.Route.Builder
import org.sonatype.nexus.repository.view.handlers.ConditionalRequestHandler
import org.sonatype.nexus.repository.view.handlers.ContentHeadersHandler
import org.sonatype.nexus.repository.view.handlers.ExceptionHandler
import org.sonatype.nexus.repository.view.handlers.FormatHighAvailabilitySupportHandler
import org.sonatype.nexus.repository.view.handlers.HandlerContributor
import org.sonatype.nexus.repository.view.handlers.HighAvailabilitySupportChecker
import org.sonatype.nexus.repository.view.handlers.TimingHandler
import org.sonatype.nexus.repository.view.matchers.ActionMatcher
import org.sonatype.nexus.repository.view.matchers.SuffixMatcher
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher

/**
 * Support for R recipes.
 *
 * @since 3.28
 */
abstract class RRecipeSupport
    extends RecipeSupport
{
  @Inject
  Provider<RSecurityFacet> securityFacet

  @Inject
  Provider<ConfigurableViewFacet> viewFacet

  @Inject
  Provider<StorageFacet> storageFacet

  @Inject
  Provider<SearchFacet> searchFacet

  @Inject
  Provider<AttributesFacet> attributesFacet

  @Inject
  FormatHighAvailabilitySupportHandler highAvailabilitySupportHandler;

  @Inject
  HighAvailabilitySupportChecker highAvailabilitySupportChecker

  @Inject
  ExceptionHandler exceptionHandler

  @Inject
  TimingHandler timingHandler

  @Inject
  SecurityHandler securityHandler

  @Inject
  RoutingRuleHandler routingRuleHandler

  @Inject
  PartialFetchHandler partialFetchHandler

  @Inject
  ConditionalRequestHandler conditionalRequestHandler

  @Inject
  ContentHeadersHandler contentHeadersHandler

  @Inject
  UnitOfWorkHandler unitOfWorkHandler

  @Inject
  HandlerContributor handlerContributor

  @Inject
  Provider<RComponentMaintenance> componentMaintenanceFacet

  @Inject
  Provider<HttpClientFacet> httpClientFacet

  @Inject
  Provider<RRestoreFacet> rRestoreFacet;

  @Inject
  Provider<RFacet> rFacet;

  protected RRecipeSupport(final Type type, final Format format) {
    super(type, format)
  }

  @Override
  boolean isFeatureEnabled() {
    return highAvailabilitySupportChecker.isSupported(getFormat().getValue());
  }

  Closure assetKindHandler = { Context context, AssetKind value ->
    context.attributes.set(AssetKind, value)
    return context.proceed()
  }

  /**
   * Matcher for all packages mapping.
   */
  static Builder packagesMatcher() {
    new Builder().matcher(
        org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.and(
            new ActionMatcher(org.sonatype.nexus.repository.http.HttpMethods.GET, org.sonatype.nexus.repository.http.
                HttpMethods.HEAD),
            packagesTokenMatcher()
        ))
  }

  /**
   * Matcher for PACKAGES.rds metadata mapping.
   *
   * This matcher should be removed after ticket fix: NEXUS-22119
   */
  static Builder metadataPackagesRdsMatcher() {
    new Builder().matcher(
        org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.and(
            new ActionMatcher(org.sonatype.nexus.repository.http.HttpMethods.GET, org.sonatype.nexus.repository.http.
                HttpMethods.HEAD),
            packagesRdsTokenMatcher()
        ))
  }

  /**
   * Matcher for all .rds metadata mapping.
   */
  static Builder metadataRdsMatcher() {
    new Builder().matcher(
        org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.and(
            new ActionMatcher(org.sonatype.nexus.repository.http.HttpMethods.GET, org.sonatype.nexus.repository.http.
                HttpMethods.HEAD),
            metadataRdsPathMatcher()
        ))
  }

  /**
   * Matcher for archive mapping.
   */
  static Builder archiveMatcher() {
    new Builder().matcher(
        org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.and(
            new ActionMatcher(org.sonatype.nexus.repository.http.HttpMethods.GET, org.sonatype.nexus.repository.http.
                HttpMethods.HEAD),
            archivePathMatcher()
        ))
  }

  /**
   * Matcher for upload mapping.
   */
  static Builder uploadMatcher() {
    new Builder().matcher(
        org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.and(
            new ActionMatcher(org.sonatype.nexus.repository.http.HttpMethods.PUT),
            allFilesTokenMatcher()
        ))
  }

  /**
   * Matcher for wrong upload mapping.
   */
  static Builder nonRArchiveUploadMatcher() {
    new Builder().matcher(
        org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.and(
            new ActionMatcher(org.sonatype.nexus.repository.http.HttpMethods.PUT),
            org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.not(archivePathMatcher())
        ))
  }

  /**
   * Path matcher for archive files.
   */
  static Matcher archivePathMatcher() {
    return org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.and(
        allFilesTokenMatcher(),
        org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.or(
            suffixMatcherForExtension('.zip'),
            suffixMatcherForExtension('.tgz'),
            suffixMatcherForExtension('.tar.gz')
        )
    )
  }

  /**
   * Path matcher for .rds metadata files.
   */
  static Matcher metadataRdsPathMatcher() {
    return org.sonatype.nexus.repository.view.matchers.logic.LogicMatchers.and(
        allFilesTokenMatcher(),
        suffixMatcherForExtension('.rds')
    )
  }

  /**
   * Token matcher for all PACKAGES files.
   */
  static TokenMatcher packagesTokenMatcher() {
    return new TokenMatcher('/{path:.+}/PACKAGES{extension:.*}')
  }

  /**
   * Token matcher for PACKAGES.rds files.
   *
   * This matcher should be removed after ticket fix: NEXUS-22119
   */
  static TokenMatcher packagesRdsTokenMatcher() {
    return new TokenMatcher('/{path:.+}/PACKAGES.rds')
  }

  /**
   * Token matcher for all files
   */
  static TokenMatcher allFilesTokenMatcher() {
    return new TokenMatcher('/{pathAndFilename:.+}')
  }

  /**
   * Suffix matcher for files with given extension.
   */
  static SuffixMatcher suffixMatcherForExtension(final String extension) {
    return new SuffixMatcher(extension)
  }
}
