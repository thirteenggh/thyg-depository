package org.sonatype.nexus.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.security.ClientInfo;
import org.sonatype.nexus.security.ClientInfoProvider;
import org.sonatype.nexus.security.authc.AuthenticationEvent;
import org.sonatype.nexus.security.authc.NexusAuthenticationEvent;

import com.google.common.eventbus.Subscribe;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Forwards {@link AuthenticationEvent} as {@link NexusAuthenticationEvent}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class AuthenticationEventSubscriber
    implements EventAware
{
  private final Provider<EventManager> eventManager;

  private final Provider<ClientInfoProvider> clientInfoProvider;

  @Inject
  public AuthenticationEventSubscriber(
      final Provider<EventManager> eventManager,
      final Provider<ClientInfoProvider> clientInfoProvider)
  {
    this.eventManager = checkNotNull(eventManager);
    this.clientInfoProvider = checkNotNull(clientInfoProvider);
  }

  @Subscribe
  public void on(final AuthenticationEvent event) {
    final ClientInfo clientInfo = clientInfoProvider.get().getCurrentThreadClientInfo();

    ClientInfo.Builder builder = ClientInfo
        .builder()
        .userId(event.getUserId());

    if (clientInfo != null) {
      builder
          .remoteIP(clientInfo.getRemoteIP())
          .userAgent(clientInfo.getUserAgent())
          .path(clientInfo.getPath());
    }

    eventManager.get().post(new NexusAuthenticationEvent(
        builder.build(),
        event.isSuccessful(),
        event.getAuthenticationFailureReasons()));
  }
}
