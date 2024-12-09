package org.sonatype.nexus.upgrade.datastore.internal;

import org.sonatype.nexus.upgrade.datastore.DatabaseMigrationStep;

import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.migration.Context;
import org.flywaydb.core.api.migration.JavaMigration;

/**
 * Wrapper for {@link DatabaseMigrationStep} for Flyway
 *
 * @since 3.29
 */
public class NexusJavaMigration implements JavaMigration
{
  private final DatabaseMigrationStep dbMigrationStep;

  public NexusJavaMigration(final DatabaseMigrationStep dbMigrationStep) {
    this.dbMigrationStep = dbMigrationStep;
  }

  @Override
  public boolean canExecuteInTransaction() {
    return dbMigrationStep.canExecuteInTransaction();
  }

  @Override
  public Integer getChecksum() {
    return dbMigrationStep.getChecksum();
  }

  @Override
  public String getDescription() {
    return dbMigrationStep.getClass().getSimpleName();
  }

  @Override
  public MigrationVersion getVersion() {
    return MigrationVersion.fromVersion(dbMigrationStep.version());
  }

  @Override
  public boolean isUndo() {
    return false;
  }

  @Override
  public void migrate(final Context context) throws Exception {
    dbMigrationStep.migrate(context.getConnection());
  }
}
