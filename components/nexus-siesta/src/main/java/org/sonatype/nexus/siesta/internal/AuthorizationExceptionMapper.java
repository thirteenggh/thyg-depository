package org.sonatype.nexus.siesta.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.sonatype.nexus.rest.ExceptionMapperSupport;

import org.apache.shiro.authz.AuthorizationException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Maps {@link AuthorizationException} to {@link Status#UNAUTHORIZED} in case that a user is logged in
 * or to an {@link Status#FORBIDDEN} in case that no user is authenticated.
 *
 * @since 2.4
 */
@Named
@Singleton
public class AuthorizationExceptionMapper
    extends ExceptionMapperSupport<AuthorizationException>
{
  private static final String AUTH_SCHEME_KEY = "auth.scheme";

  private static final String AUTH_REALM_KEY = "auth.realm";

  private static final String ANONYMOUS_LOGIN = "nexus.anonymous";

  private static final String AUTHENTICATE_HEADER = "WWW-Authenticate";

  private final Provider<HttpServletRequest> httpRequestProvider;

  @Inject
  public AuthorizationExceptionMapper(final Provider<HttpServletRequest> httpRequestProvider) {
    this.httpRequestProvider = checkNotNull(httpRequestProvider);
  }

  @Override
  protected Response convert(final AuthorizationException exception, final String id) {
    HttpServletRequest httpRequest = httpRequestProvider.get();

    if (httpRequest.getAttribute(ANONYMOUS_LOGIN) != null) {
      // user is authenticated
      String scheme = (String) httpRequest.getAttribute(AUTH_SCHEME_KEY);
      String realm = (String) httpRequest.getAttribute(AUTH_REALM_KEY);

      return Response.status(Status.UNAUTHORIZED)
          .header(AUTHENTICATE_HEADER, String.format("%s realm=\"%s\"", scheme, realm))
          .build();
    }

    return Response.status(Status.FORBIDDEN).build();
  }
}
