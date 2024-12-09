package org.sonatype.nexus.repository.npm.internal;

import org.sonatype.nexus.repository.proxy.ProxyHandler;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher.State;

/**
 * Npm specific handling for proxy repositories.
 * 
 * @since 3.0
 */
public class NpmProxyHandler
    extends ProxyHandler
{
  @Override
  protected Response buildNotFoundResponse(final Context context) {
    State state = context.getAttributes().require(TokenMatcher.State.class);
    NpmPackageId packageId = NpmPaths.packageId(state);
    return NpmResponses.packageNotFound(packageId);
  }
}
