package org.sonatype.nexus.internal.httpclient.handlers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.httpclient.config.ConnectionConfiguration;
import org.sonatype.nexus.security.PasswordHelper;

import org.apache.ibatis.type.TypeHandler;

/**
 * MyBatis {@link TypeHandler} that maps a {@link ConnectionConfiguration} to/from JSON.
 *
 * @since 3.21
 */
@Named
@Singleton
public class ConnectionConfigurationHandler
    extends HttpClientConfigurationHandler<ConnectionConfiguration>
{
  @Inject
  public ConnectionConfigurationHandler(final PasswordHelper passwordHelper) {
    super(passwordHelper);
  }
}
