package org.sonatype.nexus.upgrade.example;

import org.sonatype.nexus.common.upgrade.DependsOn;
import org.sonatype.nexus.common.upgrade.Upgrades;

@Upgrades(model = "wibble", from = "1.0", to = "2.0")
@DependsOn(model = "foo", version = "1.2")
@DependsOn(model = "bar", version = "1.1")
public class UpgradeWibble_2_0
    extends UpgradeMock
{
}
