package org.sonatype.nexus.internal.system;

import org.sonatype.nexus.common.system.FileDescriptorProvider;

import org.elasticsearch.monitor.process.ProcessProbe;

/**
 * {@link FileDescriptorProvider} that returns the file descriptor count using the Elastic {@link ProcessProbe}.
 *
 * @since 3.5
 */
public class ProcessProbeFileDescriptorProvider
    implements FileDescriptorProvider
{
  public long getFileDescriptorCount() {
    return ProcessProbe.getInstance().getMaxFileDescriptorCount();
  }
}
