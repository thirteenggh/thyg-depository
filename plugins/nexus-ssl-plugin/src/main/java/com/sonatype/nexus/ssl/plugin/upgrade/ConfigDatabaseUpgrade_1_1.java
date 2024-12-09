package com.sonatype.nexus.ssl.plugin.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.sonatype.nexus.ssl.plugin.internal.keystore.orient.OrientKeyStoreData;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Upgrades the config database to account for the new {@link OrientKeyStoreData} entity. Note that this is separate from
 * {@link TrustStoreUpgrade_2_0} to ensure the checkpoint for the config database is run (e.g. performs backup/restore).
 * 
 * @since 3.1
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.CONFIG, from = "1.0", to = "1.1")
public class ConfigDatabaseUpgrade_1_1 // NOSONAR
    extends DatabaseUpgradeSupport
{
  private final LegacyKeyStoreUpgradeService upgradeService;

  @Inject
  public ConfigDatabaseUpgrade_1_1(LegacyKeyStoreUpgradeService upgradeService) {
    this.upgradeService = checkNotNull(upgradeService);
  }

  @Override
  public void apply() throws Exception {
    upgradeService.upgradeSchema();
  }
}
