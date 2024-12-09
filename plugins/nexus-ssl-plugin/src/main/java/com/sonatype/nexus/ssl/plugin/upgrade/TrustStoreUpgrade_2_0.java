package com.sonatype.nexus.ssl.plugin.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.DependsOn;
import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Upgrades the trust store from its file-based storage to the database.
 * 
 * @since 3.1
 */
@Named
@Singleton
@Upgrades(model = TrustStoreCheckpoint.MODEL, from = "1.0", to = "2.0")
@DependsOn(model = DatabaseInstanceNames.CONFIG, version = "1.1")
public class TrustStoreUpgrade_2_0 // NOSONAR
    extends DatabaseUpgradeSupport
{
  private final LegacyKeyStoreUpgradeService upgradeService;

  @Inject
  public TrustStoreUpgrade_2_0(final LegacyKeyStoreUpgradeService upgradeService) {
    this.upgradeService = checkNotNull(upgradeService);
  }

  @Override
  public void apply() throws Exception {
    upgradeService.importKeyStoreFiles();
  }
}
