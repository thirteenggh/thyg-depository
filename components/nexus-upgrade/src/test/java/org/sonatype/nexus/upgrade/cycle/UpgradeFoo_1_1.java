package org.sonatype.nexus.upgrade.cycle;

import org.sonatype.nexus.common.upgrade.DependsOn;
import org.sonatype.nexus.common.upgrade.Upgrade;
import org.sonatype.nexus.common.upgrade.Upgrades;

@Upgrades(model = "foo", from = "1.0", to = "1.1")
@DependsOn(model = "wibble", version = "1.1")
public class UpgradeFoo_1_1
    implements Upgrade
{
  @Override
  public void apply() {
    // no-op
  }
}
