package org.sonatype.nexus.cleanup.internal.task;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.cleanup.service.CleanupService;
import org.sonatype.nexus.scheduling.TaskSupport;
import org.sonatype.nexus.logging.task.TaskLogging;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.logging.task.TaskLogType.TASK_LOG_ONLY;

/**
 * Runs cleanup via the cleanup service.
 *
 * @since 3.14
 */
@Named
@TaskLogging(TASK_LOG_ONLY)
public class CleanupTask
    extends TaskSupport
{
  private final CleanupService cleanupService;

  @Inject
  public CleanupTask(final CleanupService cleanupService) {
    this.cleanupService = checkNotNull(cleanupService);
  }
  
  @Override
  protected Object execute() {
    log.info("Starting cleanup");
    
    cleanupService.cleanup(() -> isCanceled());
    
    if (isCanceled()) {
      log.info("Cleanup was cancelled before it could finish");
    }
    else {
      log.info("Cleanup finished");
    }
    
    return null;
  }

  @Override
  public String getMessage() {
    return "Run repository cleanup";
  }
}
