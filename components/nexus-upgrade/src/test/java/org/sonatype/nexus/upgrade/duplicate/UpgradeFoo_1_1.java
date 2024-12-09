package org.sonatype.nexus.upgrade.duplicate;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.upgrade.example.UpgradeMock;

@Upgrades(model = "foo", from = "1.0", to = "1.1")
public class UpgradeFoo_1_1
    extends UpgradeMock
{
}
