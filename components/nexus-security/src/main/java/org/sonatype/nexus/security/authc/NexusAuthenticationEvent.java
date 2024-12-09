package org.sonatype.nexus.security.authc;

import java.util.Date;
import java.util.Set;

import org.sonatype.nexus.security.ClientInfo;

import static java.util.Collections.emptySet;

// FIXME: Sort out why we have 2 events: NexusAuthenticationEvent and AuthenticationEvent

/**
 * Event fired when authentication validation is performed (someone tries to log in).
 */
public class NexusAuthenticationEvent
{
  private final ClientInfo clientInfo;

  private final boolean successful;

  private final Date date;

  private final Set<AuthenticationFailureReason> authenticationFailureReasons;

  public NexusAuthenticationEvent(
      final ClientInfo info,
      final boolean successful)
  {
    this(info, successful, emptySet());
  }

  public NexusAuthenticationEvent(
      final ClientInfo info,
      final boolean successful,
      final Set<AuthenticationFailureReason> authenticationFailureReasons)
  {
    this.clientInfo = info;
    this.successful = successful;
    this.date = new Date();
    this.authenticationFailureReasons = authenticationFailureReasons;
  }

  public ClientInfo getClientInfo() {
    return clientInfo;
  }

  public boolean isSuccessful() {
    return successful;
  }

  public Date getEventDate() {
    return date;
  }

  public Set<AuthenticationFailureReason> getAuthenticationFailureReasons() {
    return authenticationFailureReasons;
  }
}
