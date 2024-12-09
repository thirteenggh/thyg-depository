package org.sonatype.nexus.repository.npm.internal;

import org.sonatype.nexus.repository.cache.NegativeCacheHandler;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.Status;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher.State;

import org.apache.http.HttpStatus;

/**
 * Npm specific handling for negative cache responses.
 *
 * @since 3.0
 */
public class NpmNegativeCacheHandler
    extends NegativeCacheHandler
{
  @Override
  protected Response buildResponse(final Status status, final Context context) {
    if (status.getCode() == HttpStatus.SC_NOT_FOUND) {
      State state = context.getAttributes().require(TokenMatcher.State.class);
      NpmPackageId packageId = NpmPaths.packageId(state);
      return NpmResponses.packageNotFound(packageId);
    }
    return super.buildResponse(status, context);
  }
}
