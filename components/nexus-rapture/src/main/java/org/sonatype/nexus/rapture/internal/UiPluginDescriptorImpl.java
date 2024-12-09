package org.sonatype.nexus.rapture.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.rapture.UiPluginDescriptor;
import org.sonatype.nexus.rapture.UiPluginDescriptorSupport;

import org.eclipse.sisu.Priority;

/**
 * Rapture {@link UiPluginDescriptor} for {@code nexus-rapture}.
 *
 * @since 3.0
 */
@Named
@Singleton
@Priority(Integer.MAX_VALUE) // always load first
public class UiPluginDescriptorImpl
    extends UiPluginDescriptorSupport
{
  @Inject
  public UiPluginDescriptorImpl() {
    super("nexus-rapture");
    setConfigClassName("NX.app.PluginConfig");
  }
}
