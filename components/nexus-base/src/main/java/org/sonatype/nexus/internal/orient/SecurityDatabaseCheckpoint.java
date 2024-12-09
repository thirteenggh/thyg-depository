package org.sonatype.nexus.internal.orient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.upgrade.Checkpoints;
import org.sonatype.nexus.orient.DatabaseCheckpointSupport;
import org.sonatype.nexus.orient.DatabaseInstance;

/**
 * Upgrade checkpoint for the "security" database.
 * 
 * @since 3.1
 */
@FeatureFlag(name = "nexus.orient.store.config")
@Named
@Singleton
@Checkpoints(model = SecurityDatabase.NAME)
public class SecurityDatabaseCheckpoint
    extends DatabaseCheckpointSupport
{
  @Inject
  public SecurityDatabaseCheckpoint(@Named(SecurityDatabase.NAME) final Provider<DatabaseInstance> databaseInstance,
                                    final ApplicationDirectories appDirectories)
  {
    super(SecurityDatabase.NAME, databaseInstance, appDirectories);
  }
}
