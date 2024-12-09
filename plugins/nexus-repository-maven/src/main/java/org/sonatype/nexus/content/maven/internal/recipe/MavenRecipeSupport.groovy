package org.sonatype.nexus.content.maven.internal.recipe

import javax.inject.Inject
import javax.inject.Provider

import org.sonatype.nexus.content.maven.MavenArchetypeCatalogFacet
import org.sonatype.nexus.content.maven.MavenContentFacet
import org.sonatype.nexus.content.maven.MavenMetadataRebuildContentFacet
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.RecipeSupport
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.content.browse.BrowseFacet
import org.sonatype.nexus.repository.content.search.SearchFacet
import org.sonatype.nexus.repository.http.PartialFetchHandler
import org.sonatype.nexus.repository.maven.MavenPathParser
import org.sonatype.nexus.repository.maven.internal.MavenSecurityFacet
import org.sonatype.nexus.repository.maven.internal.VersionPolicyHandler
import org.sonatype.nexus.repository.routing.RoutingRuleHandler
import org.sonatype.nexus.repository.security.SecurityHandler
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.handlers.ConditionalRequestHandler
import org.sonatype.nexus.repository.view.handlers.ContentHeadersHandler
import org.sonatype.nexus.repository.view.handlers.ExceptionHandler
import org.sonatype.nexus.repository.view.handlers.HandlerContributor
import org.sonatype.nexus.repository.view.handlers.LastDownloadedHandler
import org.sonatype.nexus.repository.view.handlers.TimingHandler

import groovy.transform.CompileStatic

/**
 * @since 3.25
 */
@CompileStatic
abstract class MavenRecipeSupport
    extends RecipeSupport
{

  @Inject
  Provider<ConfigurableViewFacet> viewFacet

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

  @Inject
  VersionPolicyHandler versionPolicyHandler

  @Inject
  Provider<MavenSecurityFacet> securityFacet

  @Inject
  MavenPathParser mavenPathParser

  @Inject
  MavenContentHandler mavenContentHandler

  @Inject
  MavenArchetypeCatalogHandler archetypeCatalogHandler

  @Inject
  MavenMetadataRebuildHandler mavenMetadataRebuildHandler

  @Inject
  Provider<MavenContentFacet> mavenContentFacet

  @Inject
  Provider<SearchFacet> searchFacet

  @Inject
  Provider<BrowseFacet> browseFacet

  @Inject
  Provider<MavenArchetypeCatalogFacet> mavenArchetypeCatalogFacet

  @Inject
  Provider<MavenMetadataRebuildContentFacet> mavenMetadataRebuildFacet

  @Inject
  Provider<MavenMaintenanceFacet> mavenMaintenanceFacet

  protected MavenRecipeSupport(final Type type, final Format format) {
    super(type, format)
  }
}
