package org.sonatype.nexus.security.internal;

import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.security.config.SecurityConfigurationManager;
import org.sonatype.nexus.security.config.SecurityContributor;

import org.eclipse.sisu.BeanEntry;
import org.eclipse.sisu.Mediator;

/**
 * Notifies {@link SecurityConfigurationManager} as {@link SecurityContributor}s come and go.
 *
 * @since 3.1
 */
@Named
public class SecurityContributorMediator
    extends ComponentSupport
    implements Mediator<Named, SecurityContributor, SecurityConfigurationManagerImpl>
{
  @Override
  public void add(BeanEntry<Named, SecurityContributor> entry, SecurityConfigurationManagerImpl watcher) {
    watcher.addContributor(entry.getValue());
  }

  @Override
  public void remove(BeanEntry<Named, SecurityContributor> entry, SecurityConfigurationManagerImpl watcher) {
    watcher.removeContributor(entry.getValue());
  }
}
