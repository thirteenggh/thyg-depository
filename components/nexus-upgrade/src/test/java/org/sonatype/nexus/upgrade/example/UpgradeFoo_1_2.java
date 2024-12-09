package org.sonatype.nexus.upgrade.example;

import org.sonatype.nexus.common.upgrade.Upgrades;

@Upgrades(model = "foo", from = "1.1", to = "1.2")
public class UpgradeFoo_1_2
    extends UpgradeMock
{
}
