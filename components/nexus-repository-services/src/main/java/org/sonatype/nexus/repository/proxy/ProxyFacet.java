package org.sonatype.nexus.repository.proxy;

import java.io.IOException;
import java.net.URI;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;

/**
 * A format neutral proxy facet.
 *
 * @since 3.0
 */
@Facet.Exposed
public interface ProxyFacet
    extends Facet
{
  /**
   * Obtain the content which the user has requested, either by retrieving cached content, or by fetching new or
   * updated content from the upstream repository.
   */
  @Nullable
  Content get(Context context) throws IOException;

  /**
   * Returns the root of the remote repository.
   */
  URI getRemoteUrl();

  /**
   * Invalidates all entries from proxy cache, causing all subsequent requests to attempt to fetch new or updated
   * content, if any.
   */
  void invalidateProxyCaches();
}
