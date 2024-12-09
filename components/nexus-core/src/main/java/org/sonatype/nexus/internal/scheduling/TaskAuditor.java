package org.sonatype.nexus.internal.scheduling;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.scheduling.TaskConfiguration;
import org.sonatype.nexus.scheduling.TaskInfo;
import org.sonatype.nexus.scheduling.events.TaskDeletedEvent;
import org.sonatype.nexus.scheduling.events.TaskEvent;
import org.sonatype.nexus.scheduling.events.TaskEventCanceled;
import org.sonatype.nexus.scheduling.events.TaskEventStarted;
import org.sonatype.nexus.scheduling.events.TaskEventStoppedCanceled;
import org.sonatype.nexus.scheduling.events.TaskEventStoppedDone;
import org.sonatype.nexus.scheduling.events.TaskEventStoppedFailed;
import org.sonatype.nexus.scheduling.events.TaskScheduledEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Task auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class TaskAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "tasks";

  public TaskAuditor() {
    // NOTE: scheduled is fired in case of task created or task updated; there is no easy way to determine which atm
    registerType(TaskScheduledEvent.class, "scheduled");
    registerType(TaskEventStarted.class, "started");
    registerType(TaskEventStoppedDone.class, "finished");
    registerType(TaskEventStoppedFailed.class, "failed");
    registerType(TaskEventCanceled.class, "cancel-requested");
    registerType(TaskEventStoppedCanceled.class, "canceled");
    registerType(TaskDeletedEvent.class, DELETED_TYPE);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final TaskEvent event) {
    if (isRecording()) {
      TaskInfo task = event.getTaskInfo();
      TaskConfiguration configuration = task.getConfiguration();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(type(event.getClass()));
      data.setContext(configuration.getTypeName());

      Map<String, Object> attributes = data.getAttributes();
      // TaskInfo.{id/name/message} are all delegates to configuration
      attributes.put("schedule", string(task.getSchedule()));
      attributes.put("currentState", string(task.getCurrentState()));
      attributes.put("lastRunState", string(task.getLastRunState()));

      // TODO: may want to use TaskDescriptor to provider better comprehension of the configuration
      // TODO: ... for now though, just include everything its simpler
      attributes.putAll(configuration.asMap());

      record(data);
    }
  }
}
