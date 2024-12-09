package org.sonatype.nexus.upgrade.datastore.internal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.datastore.api.DataStoreManager;
import org.sonatype.nexus.upgrade.datastore.ConfigDatabaseMigrationStep;

/**
 * Upgrade manager for the Content database
 *
 * @since 3.29
 */
@Named
@Singleton
public class ConfigUpgradeManager
    extends UpgradeManagerSupport<ConfigDatabaseMigrationStep>
{
  @Inject
  public ConfigUpgradeManager(
      final DataStoreManager dataStoreManager,
      final List<ConfigDatabaseMigrationStep> migrations)
  {
    super(dataStoreManager, DataStoreManager.CONFIG_DATASTORE_NAME, migrations);
  }
}
