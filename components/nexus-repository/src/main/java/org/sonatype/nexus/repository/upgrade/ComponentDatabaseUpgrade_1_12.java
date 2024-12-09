package org.sonatype.nexus.repository.upgrade;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;

/**
 * Empty upgrade step filling in for what is now a private model upgrade.
 *
 * @since 3.13
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.COMPONENT, from = "1.11", to = "1.12")
public class ComponentDatabaseUpgrade_1_12
    extends DatabaseUpgradeSupport
{
  @Override
  public void apply() throws Exception {
    // left intentionally blank
  }
}
