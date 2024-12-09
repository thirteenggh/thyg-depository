package org.sonatype.nexus.repository.maven.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.rapture.UiPluginDescriptorSupport;

import org.eclipse.sisu.Priority;

/**
 * Rapture {@link org.sonatype.nexus.rapture.UiPluginDescriptor} for {@code nexus-repository-maven}.
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
    super("nexus-repository-maven");
    setHasStyle(false);
    setNamespace("NX.maven");
    setConfigClassName("NX.maven.app.PluginConfig");
  }
}
