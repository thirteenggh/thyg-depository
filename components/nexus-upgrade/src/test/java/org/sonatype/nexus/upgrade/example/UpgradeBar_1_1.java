package org.sonatype.nexus.upgrade.example;

import org.sonatype.nexus.common.upgrade.DependsOn;
import org.sonatype.nexus.common.upgrade.Upgrades;

@Upgrades(model = "bar", from = "1.0", to = "1.1")
@DependsOn(model = "foo", version = "1.1")
public class UpgradeBar_1_1
    extends UpgradeMock
{
}
