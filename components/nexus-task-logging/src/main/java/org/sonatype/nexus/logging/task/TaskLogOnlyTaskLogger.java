package org.sonatype.nexus.logging.task;

import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 * {@link TaskLogger} for logging just to the task log. Stores a value in {@link MDC} for NexusLogFilter to find.
 *
 * @since 3.5
 */
public class TaskLogOnlyTaskLogger
    extends SeparateTaskLogTaskLogger
{
  TaskLogOnlyTaskLogger(final Logger log, final TaskLogInfo taskLogInfo) {
    super(log, taskLogInfo);
    MDC.put(TASK_LOG_ONLY_MDC, "true");
  }

  @Override
  protected void writeLogFileNameToNexusLog() {
    MDC.remove(TASK_LOG_ONLY_MDC);
    super.writeLogFileNameToNexusLog();
    MDC.put(TASK_LOG_ONLY_MDC, "true");
  }
}
