package org.sonatype.nexus.upgrade.datastore.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.upgrade.UpgradeService;

import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.UPGRADE;

/**
 * Default datastore {@link UpgradeService}.
 *
 * @since 3.29
 */
@Named
@ManagedLifecycle(phase = UPGRADE)
@Singleton
public class UpgradeServiceImpl
    extends StateGuardLifecycleSupport
    implements UpgradeService
{
  private final ConfigUpgradeManager configUpgradeManager;

  private final ContentUpgradeManager contentUpgradeManager;

  @Inject
  public UpgradeServiceImpl(final ConfigUpgradeManager configUpgradeManager, final ContentUpgradeManager contentUpgradeManager) {
    this.configUpgradeManager = configUpgradeManager;
    this.contentUpgradeManager = contentUpgradeManager;
  }

  @Override
  protected void doStart() throws Exception {
    configUpgradeManager.migrate();
    contentUpgradeManager.migrate();
  }
}
