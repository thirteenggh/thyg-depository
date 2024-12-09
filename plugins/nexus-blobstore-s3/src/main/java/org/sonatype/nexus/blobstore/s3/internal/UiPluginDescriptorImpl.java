package org.sonatype.nexus.blobstore.s3.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.rapture.UiPluginDescriptor;
import org.sonatype.nexus.rapture.UiPluginDescriptorSupport;

import org.eclipse.sisu.Priority;

/**
 * Rapture {@link UiPluginDescriptor} for {@code nexus-blobstore-s3}.
 *
 * @since 3.17
 */
@Named
@Singleton
@Priority(Integer.MAX_VALUE)
public class UiPluginDescriptorImpl
    extends UiPluginDescriptorSupport
{
  @Inject
  public UiPluginDescriptorImpl() {
    super("nexus-blobstore-s3");
    setHasStyle(false);
    setNamespace("NX.s3blobstore");
    setConfigClassName("NX.s3blobstore.app.PluginConfig");
  }
}
