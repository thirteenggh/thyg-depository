package org.sonatype.nexus.security.authc;

import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Factory of authentication tokens.
 *
 * @since 2.7
 */
public interface AuthenticationTokenFactory
{
  /**
   * Creates an {@link AuthenticationToken} based on give request/response, or if unable.
   */
  @Nullable
  AuthenticationToken createToken(ServletRequest request, ServletResponse response);
}
