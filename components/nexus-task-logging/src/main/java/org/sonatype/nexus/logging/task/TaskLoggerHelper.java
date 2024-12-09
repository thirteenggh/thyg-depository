package org.sonatype.nexus.logging.task;

import org.slf4j.Logger;

/**
 * Each task is executed in its own thread and its {@link TaskLogger} is stored in it here.
 *
 * @since 3.5
 */
public class TaskLoggerHelper
{
  private static final ThreadLocal<TaskLogger> context = new ThreadLocal<>();

  private TaskLoggerHelper() {
    throw new IllegalAccessError("Utility class");
  }

  public static void start(final TaskLogger taskLogger) {
    taskLogger.start();
    context.set(taskLogger);
  }

  public static TaskLogger get() {
    return context.get();
  }

  public static void finish() {
    TaskLogger taskLogger = get();
    if (taskLogger != null) {
      taskLogger.finish();
    }
    context.remove();
  }

  public static void progress(final TaskLoggingEvent event) {
    TaskLogger taskLogger = get();
    if (taskLogger != null) {
      taskLogger.progress(event);
    }
  }

  public static void progress(final Logger logger, final String message, Object... args) {
    progress(new TaskLoggingEvent(logger, message, args));
  }

  /**
   * @see TaskLogger#flush()
   */
  public static void flush() {
    TaskLogger taskLogger = get();
    if (taskLogger != null) {
      taskLogger.flush();
    }
  }
}
