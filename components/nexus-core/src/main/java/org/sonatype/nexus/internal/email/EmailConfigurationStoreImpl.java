package org.sonatype.nexus.internal.email;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.datastore.ConfigStoreSupport;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.email.EmailConfiguration;
import org.sonatype.nexus.transaction.Transactional;

/**
 * MyBatis {@link EmailConfigurationStore} implementation.
 *
 * @since 3.21
 */
@Named("mybatis")
@Singleton
public class EmailConfigurationStoreImpl
    extends ConfigStoreSupport<EmailConfigurationDAO>
    implements EmailConfigurationStore
{
  @Inject
  public EmailConfigurationStoreImpl(final DataSessionSupplier sessionSupplier) {
    super(sessionSupplier);
  }

  @Override
  public EmailConfiguration newConfiguration() {
    return new EmailConfigurationData();
  }

  @Transactional
  @Override
  public EmailConfiguration load() {
    return dao().get().orElse(null);
  }

  @Transactional
  @Override
  public void save(final EmailConfiguration configuration) {
    dao().set((EmailConfigurationData) configuration);
  }
}
