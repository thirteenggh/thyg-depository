package org.sonatype.nexus.upgrade.bad;

import org.sonatype.nexus.common.upgrade.Upgrade;
import org.sonatype.nexus.common.upgrade.Upgrades;

/**
 * Error: version goes backwards
 */
@Upgrades(model = "foo", from = "1.2", to = "1.1")
public class UpgradeFoo_1_2
    implements Upgrade
{
  @Override
  public void apply() {
    // no-op
  }
}
