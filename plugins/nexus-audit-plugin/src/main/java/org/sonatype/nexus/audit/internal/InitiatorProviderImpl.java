package org.sonatype.nexus.audit.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.audit.InitiatorProvider;
import org.sonatype.nexus.security.ClientInfo;
import org.sonatype.nexus.security.ClientInfoProvider;
import org.sonatype.nexus.security.UserIdHelper;

/**
 * Default {@link InitiatorProvider} implementation.
 *
 * @since 3.1
 */
@Named
@Singleton
public class InitiatorProviderImpl
    extends ComponentSupport
    implements InitiatorProvider
{
  private final ClientInfoProvider clientInfoProvider;

  @Inject
  public InitiatorProviderImpl(final ClientInfoProvider clientInfoProvider) {
    this.clientInfoProvider = clientInfoProvider;
  }

  /**
   * When {@link ClientInfo} is available, returns {@code user-id/remote-ip}, else returns {@code user-id}.
   */
  @Override
  public String get() {
    ClientInfo clientInfo = clientInfoProvider.getCurrentThreadClientInfo();
    if (clientInfo != null) {
      return new StringBuilder()
          .append(clientInfo.getUserid())
          .append('/')
          .append(clientInfo.getRemoteIP())
          .toString();
    }
    else {
      return UserIdHelper.get();
    }
  }
}
