package org.sonatype.nexus.scheduling.events;

import org.sonatype.nexus.scheduling.TaskInfo;

/**
 * Emitted when a task has been deleted.
 *
 * @since 3.1
 */
public class TaskDeletedEvent
    extends TaskEvent
{
  public TaskDeletedEvent(final TaskInfo taskInfo) {
    super(taskInfo);
  }
}
