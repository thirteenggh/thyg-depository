package org.sonatype.nexus.logging.task;

import org.slf4j.Logger;

import static org.sonatype.nexus.logging.task.TaskLogType.BOTH;

/**
 * Factory to create {@link TaskLogger} instances
 *
 * @since 3.5
 */
public class TaskLoggerFactory
{
  private TaskLoggerFactory() {
    throw new IllegalAccessError("Utility class");
  }

  public static TaskLogger create(final Object taskObject, final Logger log, final TaskLogInfo taskLogInfo) {
    TaskLogging taskLogging = taskObject.getClass().getAnnotation(TaskLogging.class);

    if (taskLogging == null) {
      taskLogging = TaskLoggingDefault.class.getAnnotation(TaskLogging.class);
    }

    switch (taskLogging.value()) {
      case NEXUS_LOG_ONLY:
        return new ProgressTaskLogger(log);
      case TASK_LOG_ONLY:
        return new TaskLogOnlyTaskLogger(log, taskLogInfo);
      case TASK_LOG_ONLY_WITH_PROGRESS:
        return new TaskLogWithProgressLogger(log, taskLogInfo);
      case BOTH:
      default:
        return new SeparateTaskLogTaskLogger(log, taskLogInfo);
    }
  }

  @TaskLogging(BOTH)
  private static final class TaskLoggingDefault { }
}
