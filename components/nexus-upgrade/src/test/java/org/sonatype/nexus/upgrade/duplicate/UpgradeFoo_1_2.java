package org.sonatype.nexus.upgrade.duplicate;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.upgrade.example.UpgradeMock;

@Upgrades(model = "foo", from = "1.1", to = "1.2")
public class UpgradeFoo_1_2
    extends UpgradeMock
{
}
