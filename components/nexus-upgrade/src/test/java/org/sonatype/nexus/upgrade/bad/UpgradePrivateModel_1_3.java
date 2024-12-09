package org.sonatype.nexus.upgrade.bad;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.sonatype.nexus.common.upgrade.DependsOn;
import org.sonatype.nexus.common.upgrade.Upgrade;
import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstance;

/**
 * Error: upgrade doesn't trigger any checkpoint
 */
@Upgrades(model = "privateModel", from = "1.2", to = "1.3")
@DependsOn(model = "foo", version = "1.0")
public class UpgradePrivateModel_1_3
    implements Upgrade
{
  @Inject
  public UpgradePrivateModel_1_3(@Named("foo") Provider<DatabaseInstance> database) {
    // no-op
  }

  @Override
  public void apply() {
    // no-op
  }
}
