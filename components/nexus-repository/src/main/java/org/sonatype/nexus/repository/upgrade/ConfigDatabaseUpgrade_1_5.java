package org.sonatype.nexus.repository.upgrade;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;

/**
 * This upgrade is now a no-op because the data it used to migrate is dropped by a subsequent upgrade.
 *
 * @since 3.7
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.CONFIG, from = "1.4", to = "1.5")
public class ConfigDatabaseUpgrade_1_5 // NOSONAR
    extends DatabaseUpgradeSupport
{
  @Override
  public void apply() {
    // no-op
  }
}
