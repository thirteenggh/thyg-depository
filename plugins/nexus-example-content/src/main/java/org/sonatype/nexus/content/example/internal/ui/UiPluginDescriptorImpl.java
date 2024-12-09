package org.sonatype.nexus.content.example.internal.ui;

import javax.annotation.Priority;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.rapture.UiPluginDescriptorSupport;

@Named
@Singleton
@Priority(Integer.MAX_VALUE - 200)
public class UiPluginDescriptorImpl
    extends UiPluginDescriptorSupport
{
  public UiPluginDescriptorImpl() {
    super("nexus-example-content");
    setNamespace("NX.example");
    setConfigClassName("NX.example.app.PluginConfig");
  }
}
