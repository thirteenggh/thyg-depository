package org.sonatype.nexus.content.raw.internal.recipe

import javax.inject.Inject
import javax.inject.Provider

import org.sonatype.nexus.content.raw.RawContentFacet
import org.sonatype.nexus.repository.Format
import org.sonatype.nexus.repository.RecipeSupport
import org.sonatype.nexus.repository.Type
import org.sonatype.nexus.repository.content.browse.BrowseFacet
import org.sonatype.nexus.repository.content.maintenance.SingleAssetMaintenanceFacet
import org.sonatype.nexus.repository.content.search.SearchFacet
import org.sonatype.nexus.repository.http.PartialFetchHandler
import org.sonatype.nexus.repository.raw.internal.RawSecurityFacet
import org.sonatype.nexus.repository.security.SecurityHandler
import org.sonatype.nexus.repository.view.ConfigurableViewFacet
import org.sonatype.nexus.repository.view.handlers.BrowseUnsupportedHandler
import org.sonatype.nexus.repository.view.handlers.ConditionalRequestHandler
import org.sonatype.nexus.repository.view.handlers.ContentHeadersHandler
import org.sonatype.nexus.repository.view.handlers.ExceptionHandler
import org.sonatype.nexus.repository.view.handlers.HandlerContributor
import org.sonatype.nexus.repository.view.handlers.LastDownloadedHandler
import org.sonatype.nexus.repository.view.handlers.TimingHandler

/**
 * @since 3.24
 */
abstract class RawRecipeSupport
    extends RecipeSupport
{
  @Inject
  Provider<RawSecurityFacet> securityFacet

  @Inject
  Provider<ConfigurableViewFacet> viewFacet

  @Inject
  Provider<RawContentFacet> contentFacet

  @Inject
  Provider<SingleAssetMaintenanceFacet> maintenanceFacet

  @Inject
  Provider<SearchFacet> searchFacet

  @Inject
  Provider<BrowseFacet> browseFacet

  @Inject
  ExceptionHandler exceptionHandler

  @Inject
  TimingHandler timingHandler

  @Inject
  RawIndexHtmlForwardHandler indexHtmlForwardHandler

  @Inject
  SecurityHandler securityHandler

  @Inject
  PartialFetchHandler partialFetchHandler

  @Inject
  RawContentHandler contentHandler

  @Inject
  ConditionalRequestHandler conditionalRequestHandler

  @Inject
  ContentHeadersHandler contentHeadersHandler

  @Inject
  LastDownloadedHandler lastDownloadedHandler

  @Inject
  BrowseUnsupportedHandler browseUnsupportedHandler

  @Inject
  HandlerContributor handlerContributor

  protected RawRecipeSupport(final Type type, final Format format)
  {
    super(type, format)
  }
}
