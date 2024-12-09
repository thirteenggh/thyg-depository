package org.sonatype.nexus.upgrade.gap;

import org.sonatype.nexus.common.upgrade.Upgrade;
import org.sonatype.nexus.common.upgrade.Upgrades;

@Upgrades(model = "foo", from = "1.0", to = "1.1")
public class UpgradeFoo_1_1
    implements Upgrade
{
  @Override
  public void apply() {
    // no-op
  }
}
