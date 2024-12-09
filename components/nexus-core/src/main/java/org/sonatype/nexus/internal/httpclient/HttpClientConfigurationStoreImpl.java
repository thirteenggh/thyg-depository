package org.sonatype.nexus.internal.httpclient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.datastore.ConfigStoreSupport;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;
import org.sonatype.nexus.transaction.Transactional;

/**
 * MyBatis {@link HttpClientConfigurationStore} implementation.
 *
 * @since 3.21
 */
@Named("mybatis")
@Singleton
public class HttpClientConfigurationStoreImpl
    extends ConfigStoreSupport<HttpClientConfigurationDAO>
    implements HttpClientConfigurationStore
{
  @Inject
  public HttpClientConfigurationStoreImpl(final DataSessionSupplier sessionSupplier) {
    super(sessionSupplier);
  }

  @Override
  public HttpClientConfiguration newConfiguration() {
    return new HttpClientConfigurationData();
  }

  @Transactional
  @Override
  public HttpClientConfiguration load() {
    return dao().get().orElse(null);
  }

  @Transactional
  @Override
  public void save(final HttpClientConfiguration configuration) {
    dao().set((HttpClientConfigurationData) configuration);
  }
}
