package org.sonatype.nexus.repository.npm.internal;

import javax.annotation.Priority;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.rapture.UiPluginDescriptorSupport;

/**
 * Rapture {@link org.sonatype.nexus.ui.UiPluginDescriptor} for {@code nexus-repository-npm}
 *
 * @since 3.16
 */
@Named
@Singleton
@Priority(Integer.MAX_VALUE - 300) // after nexus-proui-plugin
public class UiPluginDescriptorImpl
    extends UiPluginDescriptorSupport
{
  public UiPluginDescriptorImpl() {
    super("nexus-repository-npm");
    setHasStyle(false);
    setNamespace("NX.npm");
    setConfigClassName("NX.npm.app.PluginConfig");
  }
}
