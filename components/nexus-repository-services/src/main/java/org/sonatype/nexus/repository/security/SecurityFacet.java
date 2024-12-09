package org.sonatype.nexus.repository.security;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.view.Request;

import org.apache.shiro.authz.AuthorizationException;

/**
 * Security facet.
 *
 * @since 3.0
 */
@Facet.Exposed
public interface SecurityFacet
  extends Facet
{
  /**
   * Ensure the given request is permitted on the repository.
   *
   * @throws AuthorizationException
   */
  void ensurePermitted(Request request);
}
