package org.sonatype.nexus.common.event;

import com.google.common.eventbus.EventBus;

/**
 * Marker to indicate that component should be registered and unregistered with the synchronous {@link EventBus}.
 *
 * @since 3.0
 */
public interface EventAware
{
  /**
   * Marker for {@link EventAware} component to register and unregister with the asynchronous {@link EventBus}.
   */
  interface Asynchronous
  {
    // empty
  }
}
