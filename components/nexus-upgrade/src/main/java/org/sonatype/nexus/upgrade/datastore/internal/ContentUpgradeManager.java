package org.sonatype.nexus.upgrade.datastore.internal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.datastore.api.DataStoreManager;
import org.sonatype.nexus.upgrade.datastore.ContentDatabaseMigrationStep;

/**
 * Upgrade manager for the Content database
 *
 * @since 3.29
 */
@Named
@Singleton
public class ContentUpgradeManager
    extends UpgradeManagerSupport<ContentDatabaseMigrationStep>
{
  @Inject
  public ContentUpgradeManager(
      final DataStoreManager dataStoreManager,
      final List<ContentDatabaseMigrationStep> migrations)
  {
    super(dataStoreManager, DataStoreManager.CONTENT_DATASTORE_NAME, migrations);
  }
}
