package org.sonatype.nexus.scheduling.internal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.spi.SchedulerSPI;

import com.codahale.metrics.health.HealthCheck;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

/**
 * Scheduler health checks that reports a list of task descriptions that were recovered and had manual triggers created
 * automatically. This indicates to a user that those tasks should be reconfigured to their desired specification.
 *
 * @since 3.17
 */
@Named("调度")
@Singleton
public class SchedulerHealthCheck
    extends HealthCheck
{
  private final Provider<SchedulerSPI> scheduler;

  @Inject
  public SchedulerHealthCheck(final Provider<SchedulerSPI> scheduler) {
    this.scheduler = checkNotNull(scheduler);
  }

  @Override
  protected Result check() {
    List<String> missingTaskDescriptions = scheduler.get().getMissingTriggerDescriptions();
    return missingTaskDescriptions.isEmpty() ? Result.healthy() : Result.unhealthy(reason(missingTaskDescriptions));
  }

  private String reason(final List<String> missingTaskDescriptions) {
    String taskDescriptions = String.join(", ", missingTaskDescriptions);
    return format("%s个任务需要频率更新：%s", missingTaskDescriptions.size(), taskDescriptions);
  }
}
