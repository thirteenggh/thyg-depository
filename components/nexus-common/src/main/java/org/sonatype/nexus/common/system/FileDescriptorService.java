package org.sonatype.nexus.common.system;

/**
 * File descriptor service.
 *
 * @since 3.5
 */
public interface FileDescriptorService
{
  /**
   * Check if the OS file descriptor limit is ok. Note that this check applies to *nix systems only, Windows will
   * always return true;
   */
  boolean isFileDescriptorLimitOk();

  /**
   * Return the actual file descriptor count
   */
  long getFileDescriptorCount();

  /**
   * Return the recommended file descriptor count
   */
  long getFileDescriptorRecommended();
}
