package org.sonatype.nexus.internal.security.anonymous;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.datastore.ConfigStoreSupport;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;
import org.sonatype.nexus.transaction.Transactional;

/**
 * MyBatis {@link AnonymousConfigurationStore} implementation.
 *
 * @since 3.21
 */
@Named("mybatis")
@Singleton
public class AnonymousConfigurationStoreImpl
    extends ConfigStoreSupport<AnonymousConfigurationDAO>
    implements AnonymousConfigurationStore
{
  @Inject
  public AnonymousConfigurationStoreImpl(final DataSessionSupplier sessionSupplier) {
    super(sessionSupplier);
  }

  @Override
  public AnonymousConfiguration newConfiguration() {
    return new AnonymousConfigurationData();
  }

  @Transactional
  @Override
  public AnonymousConfiguration load() {
    return dao().get().orElse(null);
  }

  @Transactional
  @Override
  public void save(final AnonymousConfiguration configuration) {
    dao().set((AnonymousConfigurationData) configuration);
  }
}
