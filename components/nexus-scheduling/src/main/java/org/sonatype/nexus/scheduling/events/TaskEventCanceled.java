package org.sonatype.nexus.scheduling.events;

import org.sonatype.nexus.scheduling.Cancelable;
import org.sonatype.nexus.scheduling.TaskInfo;

/**
 * Event fired when {@link Cancelable} NX Task is running and has been canceled.
 *
 * Fired in the moment cancellation was applied, the task is probably still running and will stop when it detects
 * request for cancellation.
 *
 * If this event was emitted, the last event sent in this run for the task is {@link TaskEventStoppedCanceled}.
 *
 * @since 2.0
 */
public class TaskEventCanceled
    extends TaskEvent
{
  public TaskEventCanceled(final TaskInfo taskInfo) {
    super(taskInfo);
  }
}
