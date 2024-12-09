package org.sonatype.nexus.scheduling.internal;

import java.util.concurrent.Future;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.Freezable;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.scheduling.TaskInfo;
import org.sonatype.nexus.scheduling.spi.SchedulerSPI;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.TASKS;

/**
 * Manages activation/passivation of the scheduler.
 *
 * @since 3.0
 */
@Named
@ManagedLifecycle(phase = TASKS)
@Priority(Integer.MIN_VALUE) // start scheduler at the end of this phase
@Singleton
public class TaskActivation
    extends StateGuardLifecycleSupport
    implements Freezable
{
  private final SchedulerSPI scheduler;

  private volatile boolean frozen;

  @Inject
  public TaskActivation(final SchedulerSPI scheduler) {
    this.scheduler = checkNotNull(scheduler);
  }

  @Override
  protected void doStart() throws Exception {
    if (!isFrozen()) {
      scheduler.resume();
    }
  }

  @Override
  protected void doStop() throws Exception {
    scheduler.pause();
  }

  @Override
  public boolean isFrozen() {
    return frozen;
  }

  @Override
  public void freeze() {
    frozen = true;
    if (isStarted()) {
      scheduler.pause();
      scheduler.listsTasks().stream()
          .filter(taskInfo -> !maybeCancel(taskInfo))
          .forEach(taskInfo -> log.warn("Unable to cancel task: {}", taskInfo.getName()));
    }
  }

  @Override
  public void unfreeze() {
    frozen = false;
    if (isStarted()) {
      scheduler.resume();
    }
  }

  private boolean maybeCancel(final TaskInfo taskInfo) {
    Future<?> future = taskInfo.getCurrentState().getFuture();
    return future == null || future.cancel(false);
  }
}
