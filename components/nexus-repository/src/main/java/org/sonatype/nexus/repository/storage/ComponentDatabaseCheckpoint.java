package org.sonatype.nexus.repository.storage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.upgrade.Checkpoints;
import org.sonatype.nexus.orient.DatabaseCheckpointSupport;
import org.sonatype.nexus.orient.DatabaseInstance;

/**
 * Upgrade checkpoint for the "component" database.
 * 
 * @since 3.1
 */
@Named
@Singleton
@Checkpoints(model = ComponentDatabase.NAME)
public class ComponentDatabaseCheckpoint
    extends DatabaseCheckpointSupport
{
  @Inject
  public ComponentDatabaseCheckpoint(@Named(ComponentDatabase.NAME) final Provider<DatabaseInstance> databaseInstance,
                                     final ApplicationDirectories appDirectories)
  {
    super(ComponentDatabase.NAME, databaseInstance, appDirectories);
  }
}
