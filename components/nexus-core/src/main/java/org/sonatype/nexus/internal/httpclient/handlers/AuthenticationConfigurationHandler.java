package org.sonatype.nexus.internal.httpclient.handlers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.httpclient.config.AuthenticationConfiguration;
import org.sonatype.nexus.security.PasswordHelper;

import org.apache.ibatis.type.TypeHandler;

/**
 * MyBatis {@link TypeHandler} that maps an {@link AuthenticationConfiguration} to/from JSON.
 *
 * @since 3.21
 */
@Named
@Singleton
public class AuthenticationConfigurationHandler
    extends HttpClientConfigurationHandler<AuthenticationConfiguration>
{
  @Inject
  public AuthenticationConfigurationHandler(final PasswordHelper passwordHelper) {
    super(passwordHelper);
  }
}
