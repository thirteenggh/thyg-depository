package org.sonatype.nexus.repository.npm.internal.search.legacy;

import java.io.IOException;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.view.Content;

import org.joda.time.DateTime;

/**
 * npm search index facet.
 *
 * @since 3.0
 * @deprecated No longer actively used by npm upstream, replaced by v1 search api (NEXUS-13150).
 */
@Deprecated
@Facet.Exposed
public interface NpmSearchIndexFacet
    extends Facet
{
  /**
   * Fetches the index document.
   */
  Content searchIndex(@Nullable final DateTime since) throws IOException;

  /**
   * Invalidates cached index document, if applicable.
   */
  void invalidateCachedSearchIndex();
}
