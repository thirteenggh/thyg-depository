package org.sonatype.repository.conan.internal.security.token;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.Status;
import org.sonatype.nexus.repository.view.payloads.StringPayload;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.http.HttpStatus.OK;
import static org.sonatype.nexus.repository.http.HttpStatus.UNAUTHORIZED;
import static org.sonatype.nexus.repository.view.ContentTypes.TEXT_PLAIN;

/**
 * @since 3.28
 */
@Named
public class ConanTokenFacetImpl
    extends FacetSupport
    implements ConanTokenFacet
{
  private final ConanTokenManager conanTokenManager;

  @Inject
  public ConanTokenFacetImpl(final ConanTokenManager conanTokenManager) {
    this.conanTokenManager = checkNotNull(conanTokenManager);
  }

  @Override
  public Response login(final Context context) {
    String token = conanTokenManager.login();
    if(null != token) {
      return new Response.Builder()
          .status(Status.success(OK))
          .payload(new StringPayload(token, TEXT_PLAIN))
          .build();
    }
    return badCredentials("Bad username or password");
  }

  @Override
  public Response user(final Context context) {
    String user = conanTokenManager.user();
    if(null != user) {
      return new Response.Builder()
          .status(Status.success(OK))
          .payload(new StringPayload(user, TEXT_PLAIN))
          .build();
    }
    return badCredentials("Unknown user");
  }

  @Override
  public Response logout(final Context context) {
    return null;
  }


  static Response badCredentials(final String message) {
    return new Response.Builder()
        .status(Status.failure(UNAUTHORIZED))
        .payload(new StringPayload(message, TEXT_PLAIN))
        .build();
  }
}
