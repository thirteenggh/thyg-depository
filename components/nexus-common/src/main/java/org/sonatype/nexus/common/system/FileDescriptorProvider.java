package org.sonatype.nexus.common.system;

/**
 * Provides a way to get the OS file descriptor count
 *
 * @since 3.5
 */
public interface FileDescriptorProvider
{
  /**
   * @return the operating systems maximum file descriptor count or -1 if this is not supported (e.g. Windows)
   */
  long getFileDescriptorCount();
}
