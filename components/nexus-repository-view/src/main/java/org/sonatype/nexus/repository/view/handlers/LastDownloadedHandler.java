package org.sonatype.nexus.repository.view.handlers;

import org.sonatype.nexus.repository.view.Handler;

/**
 * Updates the asset last downloaded time
 *
 * This interface helps bridge to the real implementation which now lives in another module.
 *
 * @since 3.22
 */
public interface LastDownloadedHandler
    extends Handler
{
  // intentionally blank
}
