package org.sonatype.nexus.internal.web;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.sonatype.nexus.security.ClientInfo;
import org.sonatype.nexus.security.ClientInfoProvider;
import org.sonatype.nexus.security.UserIdHelper;

import com.google.common.net.HttpHeaders;
import com.google.inject.OutOfScopeException;
import com.google.inject.ProvisionException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@link ClientInfoProvider}
 *
 * @since 3.0
 */
@Named
@Singleton
public class ClientInfoProviderImpl
    implements ClientInfoProvider
{
  private final Provider<HttpServletRequest> httpRequestProvider;

  @Inject
  public ClientInfoProviderImpl(final Provider<HttpServletRequest> httpRequestProvider) {
    this.httpRequestProvider = checkNotNull(httpRequestProvider);
  }

  @Override
  @Nullable
  public ClientInfo getCurrentThreadClientInfo() {
    try {
      HttpServletRequest request = httpRequestProvider.get();
      return ClientInfo
          .builder()
          .userId(UserIdHelper.get())
          .remoteIP(request.getRemoteAddr())
          .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
          .path(request.getServletPath())
          .build();
    }
    catch (ProvisionException | OutOfScopeException e) {
      // ignore; this happens when called out of scope of http request
      return null;
    }
  }
}
