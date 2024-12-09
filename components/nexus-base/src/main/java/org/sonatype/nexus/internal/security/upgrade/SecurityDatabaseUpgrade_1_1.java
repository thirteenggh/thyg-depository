package org.sonatype.nexus.internal.security.upgrade;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;

/**
 * Empty upgrade step filling in for what is now a private model upgrade.
 *
 * @since 3.17
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.SECURITY, from = "1.0", to = "1.1")
public class SecurityDatabaseUpgrade_1_1 // NOSONAR
    extends DatabaseUpgradeSupport
{
  @Override
  public void apply() throws Exception {
    // left intentionally blank
  }
}
