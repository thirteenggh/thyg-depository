package org.sonatype.nexus.common.event;

/**
 * Event mix-in to determine whether or not an event is local in a distributed configuration.
 *
 * @since 3.21
 */
public interface HasLocality {
  /**
   * @return {@code true} if this is a local event
   */
  boolean isLocal();
}
