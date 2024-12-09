package org.sonatype.nexus.upgrade.bad;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.sonatype.nexus.common.upgrade.Upgrade;
import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstance;

/**
 * Error: "foo" injected but no matching @DependsOn
 */
@Upgrades(model = "privateModel", from = "1.1", to = "1.2")
public class UpgradePrivateModel_1_2
    implements Upgrade
{
  @Inject
  public UpgradePrivateModel_1_2(@Named("foo") Provider<DatabaseInstance> database) {
    // no-op
  }

  @Override
  public void apply() {
    // no-op
  }
}
