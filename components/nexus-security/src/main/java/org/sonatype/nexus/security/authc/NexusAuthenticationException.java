package org.sonatype.nexus.security.authc;

import java.util.Set;

import org.apache.shiro.authc.AccountException;

/**
 * Exception with details about a failed authentication attempt.
 *
 * @since 3.22
 */
public class NexusAuthenticationException
    extends AccountException
{
  private final Set<AuthenticationFailureReason> authenticationFailureReasons;

  public NexusAuthenticationException(
      final String cause,
      final Set<AuthenticationFailureReason> authenticationFailureReason)
  {
    super(cause);
    this.authenticationFailureReasons = authenticationFailureReason;
  }

  public Set<AuthenticationFailureReason> getAuthenticationFailureReasons() {
    return authenticationFailureReasons;
  }
}
