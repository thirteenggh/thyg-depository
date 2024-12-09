package org.sonatype.nexus.upgrade.example;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.sonatype.nexus.common.upgrade.DependsOn;
import org.sonatype.nexus.common.upgrade.Upgrade;
import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstance;

@Upgrades(model = "privateModel", from = "1.0", to = "1.1")
@DependsOn(model = "foo", version = "1.0", checkpoint = true)
public class UpgradePrivateModel_1_1
    implements Upgrade
{
  @Inject
  public UpgradePrivateModel_1_1(@Named("foo") Provider<DatabaseInstance> database) {
    // no-op
  }

  @Override
  public void apply() {
    // no-op
  }
}
