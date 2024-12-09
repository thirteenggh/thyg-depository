package org.sonatype.nexus.repository.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;

import com.orientechnologies.orient.core.sql.OCommandSQL;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.orient.DatabaseInstanceNames.CONFIG;

/**
 * Upgrades existing {@code blobstore.rebuildComponentDB} tasks to set {@code restoreBlobs = true} which
 * will replicate the previous behavior of the task.
 *
 * @since 3.6
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.CONFIG, from = "1.2", to = "1.3")
public class ConfigDatabaseUpgrade_1_3 // NOSONAR
    extends DatabaseUpgradeSupport
{
  private final OCommandSQL updateRestoreTasksQuery = new OCommandSQL(
    "UPDATE quartz_job_detail SET value_data.jobDataMap.restoreBlobs = 'true' WHERE value_data.jobDataMap['.typeId'] = 'blobstore.rebuildComponentDB'"
  );

  private final Provider<DatabaseInstance> databaseInstance;

  @Inject
  public ConfigDatabaseUpgrade_1_3(@Named(CONFIG) final Provider<DatabaseInstance> databaseInstance)
  {
    this.databaseInstance = checkNotNull(databaseInstance);
  }

  @Override
  public void apply() throws Exception {
    withDatabaseAndClass(databaseInstance, "quartz_job_detail", (db, type) ->
      db.command(updateRestoreTasksQuery).execute()
    );
  }
}
