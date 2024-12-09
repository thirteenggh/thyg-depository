package org.sonatype.nexus.scheduling.events;

import org.sonatype.nexus.scheduling.TaskInfo;

/**
 * Base class for events fired when a task is stopped, done (whatever the outcome is: finished, cancelled or failed).
 *
 * @since 2.0
 */
public abstract class TaskEventStopped
    extends TaskEvent
{
  public TaskEventStopped(final TaskInfo taskInfo) {
    super(taskInfo);
  }
}
