package org.sonatype.nexus.scheduling.events;

import org.sonatype.nexus.scheduling.TaskInfo;

/**
 * Emitted when a task has been scheduled.
 *
 * @since 3.1
 */
public class TaskScheduledEvent
    extends TaskEvent
{
  public TaskScheduledEvent(final TaskInfo taskInfo) {
    super(taskInfo);
  }
}
