package org.sonatype.repository.conan.internal.security.token;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.Facet.Exposed;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;

/**
 * @since 3.28
 */
@Exposed
public interface ConanTokenFacet
    extends Facet
{
  /**
   * Performs a login for user authenticated in the request (creates token and returns login specific response).
   */
  Response login(Context context);

  /**
   * Determines if the user is currently authenticated
   */
  Response user(Context context);

  /**
   * Performs a log-out for currently authenticated user (deletes the token if found and returns logout specific
   * response).
   */
  Response logout(Context context);
}
