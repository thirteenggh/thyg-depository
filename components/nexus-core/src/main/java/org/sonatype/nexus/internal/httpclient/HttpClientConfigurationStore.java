package org.sonatype.nexus.internal.httpclient;

import javax.annotation.Nullable;

import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;

/**
 * {@link HttpClientConfiguration} store.
 *
 * @since 3.0
 */
public interface HttpClientConfigurationStore
{
  @Nullable
  HttpClientConfiguration load();

  void save(HttpClientConfiguration configuration);

  /**
   * @since 3.20
   */
  HttpClientConfiguration newConfiguration();
}
