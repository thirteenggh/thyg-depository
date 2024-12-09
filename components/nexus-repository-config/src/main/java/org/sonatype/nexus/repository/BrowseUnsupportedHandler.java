package org.sonatype.nexus.repository;

import org.sonatype.nexus.repository.recipe.Handler;
import org.sonatype.nexus.repository.recipe.Route;

/**
 * Handles routes that don't support browse.
 *
 * This interface helps bridge to the real implementation which now lives in another module.
 *
 * @since 3.22
 */
public interface BrowseUnsupportedHandler
    extends Handler
{
  Route getRoute();
}
