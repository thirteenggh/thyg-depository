package org.sonatype.nexus.internal.httpclient.handlers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.httpclient.config.ProxyConfiguration;
import org.sonatype.nexus.security.PasswordHelper;

import org.apache.ibatis.type.TypeHandler;

/**
 * MyBatis {@link TypeHandler} that maps a {@link ProxyConfiguration} to/from JSON.
 *
 * @since 3.21
 */
@Named
@Singleton
public class ProxyConfigurationHandler
    extends HttpClientConfigurationHandler<ProxyConfiguration>
{
  @Inject
  public ProxyConfigurationHandler(final PasswordHelper passwordHelper) {
    super(passwordHelper);
  }
}
