package org.sonatype.nexus.scheduling.events;

import org.sonatype.nexus.scheduling.TaskInfo;

/**
 * Event fired when a task is stopped, is cleanly done what was it doing (whatever the ending resolution is: finished,
 * cancelled or failed).
 *
 * @since 2.0
 */
public class TaskEventStoppedDone
    extends TaskEventStopped
{
  public TaskEventStoppedDone(final TaskInfo taskInfo) {
    super(taskInfo);
  }
}
