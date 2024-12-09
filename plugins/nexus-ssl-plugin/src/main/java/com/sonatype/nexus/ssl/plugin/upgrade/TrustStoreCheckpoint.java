package com.sonatype.nexus.ssl.plugin.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Checkpoint;
import org.sonatype.nexus.common.upgrade.Checkpoints;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Upgrade checkpoint for the legacy file-based trust store. It exists for the sole purpose of removing the legacy files
 * after their import into the database.
 * 
 * @since 3.1
 */
@Named
@Singleton
@Checkpoints(model = TrustStoreCheckpoint.MODEL, local = true)
public class TrustStoreCheckpoint
    implements Checkpoint
{
  public static final String MODEL = "truststore";

  private final LegacyKeyStoreUpgradeService upgrade;

  @Inject
  public TrustStoreCheckpoint(final LegacyKeyStoreUpgradeService upgrade) {
    this.upgrade = checkNotNull(upgrade);
  }

  @Override
  public void begin(String version) throws Exception {
    // no-op
  }

  @Override
  public void commit() throws Exception {
    // no-op
  }

  @Override
  public void rollback() throws Exception {
    // no-op
  }

  @Override
  public void end() {
    upgrade.deleteKeyStoreFiles();
  }
}
