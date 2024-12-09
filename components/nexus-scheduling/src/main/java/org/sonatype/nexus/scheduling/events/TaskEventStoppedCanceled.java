package org.sonatype.nexus.scheduling.events;

import org.sonatype.nexus.scheduling.TaskInfo;

/**
 * Event fired when a task is cancelled (more precisely, not WHEN is cancelled, but when task detected that is being
 * canceled and gives up the work).
 *
 * @since 2.0
 */
public class TaskEventStoppedCanceled
    extends TaskEventStopped
{
  public TaskEventStoppedCanceled(final TaskInfo taskInfo) {
    super(taskInfo);
  }
}
