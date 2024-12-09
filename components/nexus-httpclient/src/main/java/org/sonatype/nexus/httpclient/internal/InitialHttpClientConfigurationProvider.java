package org.sonatype.nexus.httpclient.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.httpclient.HttpClientManager;
import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Initial {@link HttpClientConfiguration} provider.
 *
 * @since 3.0
 */
@Named("initial")
@Singleton
public class InitialHttpClientConfigurationProvider
  implements Provider<HttpClientConfiguration>
{
  private final HttpClientManager clientManager;

  @Inject
  public InitialHttpClientConfigurationProvider(final HttpClientManager clientManager) {
    this.clientManager = checkNotNull(clientManager);
  }

  @Override
  public HttpClientConfiguration get() {
    HttpClientConfiguration configuration = clientManager.newConfiguration();
    // TODO:
    return configuration;
  }
}
