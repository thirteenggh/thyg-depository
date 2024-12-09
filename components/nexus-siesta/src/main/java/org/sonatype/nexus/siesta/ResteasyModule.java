package org.sonatype.nexus.siesta;

import org.sonatype.nexus.siesta.internal.resteasy.ComponentContainerImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.jboss.resteasy.core.Dispatcher;

/**
 * RESTEasy module.
 *
 * @since 3.0
 */
public class ResteasyModule
  extends AbstractModule
{
  @Override
  protected void configure() {
    // eager binding so we can register RESTEasy with JAX-RS as early as possible
    bind(ComponentContainer.class).to(ComponentContainerImpl.class).asEagerSingleton();
  }

  /**
   * Expose RESTEasy {@link Dispatcher} binding.
   */
  @Provides
  public Dispatcher dispatcher(final ComponentContainer container) {
    return ((ComponentContainerImpl)container).getDispatcher();
  }
}
