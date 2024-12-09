package org.sonatype.nexus.quartz.internal.orient;

/**
 * Trigger event.
 *
 * @since 3.1
 */
public interface TriggerEvent
{
  boolean isLocal();

  TriggerEntity getTrigger();
}
