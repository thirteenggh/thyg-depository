package org.sonatype.nexus.quartz.internal.orient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.quartz.internal.QuartzSchedulerSPI;

import com.google.common.eventbus.Subscribe;
import org.quartz.SchedulerException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Event inspector that updates the local quartz instance based on remote events.
 *
 * @since 3.21
 */
@Named
@FeatureFlag(name = "nexus.clustered")
@Singleton
public class DistributedQuartzEventInspector
    extends ComponentSupport
    implements EventAware, EventAware.Asynchronous
{
  private final QuartzSchedulerSPI schedulerSPI;

  @Inject
  public DistributedQuartzEventInspector(final QuartzSchedulerSPI schedulerSPI) {
    this.schedulerSPI = checkNotNull(schedulerSPI);
  }

  @Subscribe
  public void on(JobCreatedEvent event) {
    if (!event.isLocal() && schedulerSPI.isStarted()) {
      schedulerSPI.remoteJobCreated(event.getJob().getValue());
    }
  }

  @Subscribe
  public void on(JobUpdatedEvent event) throws SchedulerException {
    if (!event.isLocal() && schedulerSPI.isStarted()) {
      schedulerSPI.remoteJobUpdated(event.getJob().getValue());
    }
  }

  @Subscribe
  public void on(JobDeletedEvent event) throws SchedulerException {
    if (!event.isLocal() && schedulerSPI.isStarted()) {
      schedulerSPI.remoteJobDeleted(event.getJob().getValue());
    }
  }

  @Subscribe
  public void on(TriggerCreatedEvent event) throws SchedulerException {
    if (!event.isLocal() && schedulerSPI.isStarted()) {
      schedulerSPI.remoteTriggerCreated(event.getTrigger().getValue());
    }
  }

  @Subscribe
  public void on(TriggerUpdatedEvent event) throws SchedulerException {
    if (!event.isLocal() && schedulerSPI.isStarted()) {
      schedulerSPI.remoteTriggerUpdated(event.getTrigger().getValue());
    }
  }

  @Subscribe
  public void on(TriggerDeletedEvent event) throws SchedulerException {
    if (!event.isLocal() && schedulerSPI.isStarted()) {
      schedulerSPI.remoteTriggerDeleted(event.getTrigger().getValue());
    }
  }
}
