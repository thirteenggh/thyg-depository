package org.sonatype.nexus.tasklog;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * @since 3.5
 */
@Named
@Singleton
public class TaskLogCleanupTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "tasklog.cleanup";

  @Inject
  public TaskLogCleanupTaskDescriptor() {
    super(TYPE_ID, TaskLogCleanupTask.class, "Task log cleanup", TaskDescriptorSupport.NOT_VISIBLE,
        TaskDescriptorSupport.NOT_EXPOSED);
  }
}
