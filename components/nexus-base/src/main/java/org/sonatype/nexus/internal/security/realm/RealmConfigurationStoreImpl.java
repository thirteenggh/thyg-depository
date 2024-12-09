package org.sonatype.nexus.internal.security.realm;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.datastore.ConfigStoreSupport;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.security.realm.RealmConfigurationStore;
import org.sonatype.nexus.transaction.Transactional;

/**
 * MyBatis {@link RealmConfigurationStore} implementation.
 *
 * @since 3.21
 */
@Named("mybatis")
@Singleton
public class RealmConfigurationStoreImpl
    extends ConfigStoreSupport<RealmConfigurationDAO>
    implements RealmConfigurationStore
{
  @Inject
  public RealmConfigurationStoreImpl(final DataSessionSupplier sessionSupplier) {
    super(sessionSupplier);
  }

  @Override
  public RealmConfiguration newEntity() {
    return new RealmConfigurationData();
  }

  @Transactional
  @Override
  public RealmConfiguration load() {
    return dao().get().orElse(null);
  }

  @Transactional
  @Override
  public void save(final RealmConfiguration configuration) {
    dao().set((RealmConfigurationData) configuration);
  }
}
