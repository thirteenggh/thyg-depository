package org.sonatype.nexus.commands.internal;

import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;

import org.apache.karaf.shell.commands.info.InfoProvider;
import org.eclipse.sisu.BeanEntry;
import org.eclipse.sisu.Mediator;
import org.osgi.framework.BundleContext;

// FIXME: This still does not seem to do the trick, to get custom InfoProviders available in shell:info

/**
 * Manages registration of Karaf {@link InfoProvider} instances.
 *
 * @since 3.0
 */
@Named
public class InfoProviderMediator
    extends ComponentSupport
    implements Mediator<Named, InfoProvider, BundleContext>
{
  @Override
  public void add(final BeanEntry<Named, InfoProvider> beanEntry, final BundleContext bundleContext)
      throws Exception
  {
    log.debug("Adding: {}", beanEntry);
    bundleContext.registerService(InfoProvider.class, beanEntry.getValue(), null);
  }

  @Override
  public void remove(final BeanEntry<Named, InfoProvider> beanEntry, final BundleContext bundleContext)
      throws Exception
  {
    // TODO: implement remove
  }
}
