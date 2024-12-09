package org.sonatype.nexus.onboarding.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.rapture.UiPluginDescriptor;
import org.sonatype.nexus.rapture.UiPluginDescriptorSupport;

import org.eclipse.sisu.Priority;

/**
 * Rapture {@link UiPluginDescriptor} for {@code nexus-onboarding-plugin}.
 *
 * @since 3.17
 */
@Named
@Singleton
@Priority(Integer.MAX_VALUE - 300) // after nexus-proui-plugin
public class UiPluginDescriptorImpl
    extends UiPluginDescriptorSupport
{
  @Inject
  public UiPluginDescriptorImpl() {
    super("nexus-onboarding-plugin");
    setNamespace("NX.onboarding");
    setConfigClassName("NX.onboarding.app.PluginConfig");
  }
}
