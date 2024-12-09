package org.sonatype.nexus.scheduling.events;

import org.sonatype.nexus.scheduling.TaskInfo;

/**
 * Emitted when a task has started running (not to be confused with {@link TaskEventStarted}).
 *
 * @since 3.1
 */
public class TaskStartedRunningEvent
    extends TaskEvent
{
  public TaskStartedRunningEvent(final TaskInfo taskInfo) {
    super(taskInfo);
  }
}
