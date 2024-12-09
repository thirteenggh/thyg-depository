package org.sonatype.nexus.repository.httpclient;

import javax.inject.Named;
import javax.inject.Singleton;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.sonatype.nexus.repository.http.HttpStatus.PROXY_AUTHENTICATION_REQUIRED;

/**
 * Default configuration for auto-blocking.
 *
 * @since 3.14
 */
@Named
@Singleton
public class DefaultAutoBlockConfiguration
  implements AutoBlockConfiguration
{
  @Override
  public boolean shouldBlock(final int statusCode) {
    return statusCode == SC_UNAUTHORIZED || statusCode == PROXY_AUTHENTICATION_REQUIRED || statusCode >= 500;
  }
}
