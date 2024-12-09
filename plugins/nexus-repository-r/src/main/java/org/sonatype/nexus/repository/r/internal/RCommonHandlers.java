package org.sonatype.nexus.repository.r.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.view.Handler;

/**
 * R common handlers.
 *
 * @since 3.28
 */
@Named
@Singleton
public final class RCommonHandlers
    extends ComponentSupport
{
  /**
   * Handle request of currently not supported metadata
   *
   * Related to:  NEXUS-22119
   */
  final Handler notSupportedMetadataRequest =
      context -> HttpResponses.notFound("This metadata type is not supported for now.");
}
