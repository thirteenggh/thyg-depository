package org.sonatype.nexus.upgrade.duplicate;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.upgrade.example.UpgradeMock;

@Upgrades(model = "foo", from = "1.2", to = "1.3")
public class UpgradeFoo_1_3
    extends UpgradeMock
{
}
