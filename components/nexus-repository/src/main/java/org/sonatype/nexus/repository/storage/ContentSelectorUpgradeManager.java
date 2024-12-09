package org.sonatype.nexus.repository.storage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.selector.CselSelector;
import org.sonatype.nexus.selector.SelectorFactory;
import org.sonatype.nexus.selector.SelectorManager;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.TASKS;

/**
 * @since 3.6
 */
@Named
@ManagedLifecycle(phase = TASKS)
@Singleton
public class ContentSelectorUpgradeManager
    extends StateGuardLifecycleSupport
{
  private final SelectorFactory selectorFactory;

  private final SelectorManager selectorManager;

  @Inject
  public ContentSelectorUpgradeManager(final SelectorFactory selectorFactory, final SelectorManager selectorManager) {
    this.selectorFactory = checkNotNull(selectorFactory);
    this.selectorManager = checkNotNull(selectorManager);
  }

  @Override
  protected void doStart() throws Exception {
    selectorManager.browseJexl().forEach(config -> {
      String expression = config.getAttributes().get("expression");
      String name = config.getName();

      log.debug("Attempting to upgrade JEXL content selector {} to CSEL, expression={}", name, expression);

      try {
        selectorFactory.validateSelector(CselSelector.TYPE, expression);
        config.setType(CselSelector.TYPE);
        selectorManager.update(config);
      }
      catch (Exception e) {
        log.warn("Could not upgrade JEXL content selector {} to CSEL, expression={}",
            name, expression, log.isDebugEnabled() ? e : null);
      }
    });
  }
}
