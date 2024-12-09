package org.sonatype.nexus.tasklog;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.logging.task.TaskLogging;
import org.sonatype.nexus.scheduling.Cancelable;
import org.sonatype.nexus.scheduling.TaskSupport;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.logging.task.TaskLogType.NEXUS_LOG_ONLY;

/**
 * Background task (hidden from users) that cleans up old log files.
 *
 * @since 3.5
 */
@Named
@TaskLogging(NEXUS_LOG_ONLY)
public class TaskLogCleanupTask
    extends TaskSupport
    implements Cancelable
{
  private final TaskLogCleanup taskLogCleanup;

  @Inject
  public TaskLogCleanupTask(final TaskLogCleanup taskLogCleanup) {
    this.taskLogCleanup = checkNotNull(taskLogCleanup);
  }

  @Override
  protected Void execute() throws Exception {
    taskLogCleanup.cleanup();
    return null;
  }

  @Override
  public String getMessage() {
    return "Remove old task log files";
  }
}
