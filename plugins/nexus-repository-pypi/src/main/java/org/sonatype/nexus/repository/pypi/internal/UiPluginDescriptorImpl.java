package org.sonatype.nexus.repository.pypi.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.rapture.UiPluginDescriptorSupport;

import org.eclipse.sisu.Priority;

/**
 * Rapture {@link org.sonatype.nexus.ui.UiPluginDescriptor} for {@code nexus-repository-pypi}
 *
 * @since 3.15
 */
@Named
@Singleton
@Priority(Integer.MAX_VALUE - 300) // after proui
public class UiPluginDescriptorImpl
    extends UiPluginDescriptorSupport
{
  @Inject
  public UiPluginDescriptorImpl() {
    super("nexus-repository-pypi");
    setHasStyle(false);
    setNamespace("NX.pypi");
    setConfigClassName("NX.pypi.app.PluginConfig");
  }
}
