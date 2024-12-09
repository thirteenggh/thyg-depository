package org.sonatype.nexus.internal.scheduling;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.event.EventAware.Asynchronous;
import org.sonatype.nexus.email.EmailManager;
import org.sonatype.nexus.scheduling.Task;
import org.sonatype.nexus.scheduling.TaskInfo;
import org.sonatype.nexus.scheduling.TaskNotificationCondition;
import org.sonatype.nexus.scheduling.TaskNotificationMessageGenerator;
import org.sonatype.nexus.scheduling.events.TaskEventStoppedDone;
import org.sonatype.nexus.scheduling.events.TaskEventStoppedFailed;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link EventAware} that will send notification email (if necessary) in case of a completed or failed {@link Task}.
 */
@Singleton
@Named
public class NexusTaskNotificationEmailSender
    extends ComponentSupport
    implements EventAware, Asynchronous
{
  private final Provider<EmailManager> emailManager;

  private final Map<String, TaskNotificationMessageGenerator> taskNotificationMessageGenerators;

  @Inject
  public NexusTaskNotificationEmailSender(final Provider<EmailManager> emailManager,
                                          final Map<String, TaskNotificationMessageGenerator> taskNotificationMessageGenerators) {
    this.emailManager = checkNotNull(emailManager);
    this.taskNotificationMessageGenerators = checkNotNull(taskNotificationMessageGenerators);
  }

  /**
   * Sends alert emails if necessary on failure.
   */
  @Subscribe
  @AllowConcurrentEvents
  public void on(final TaskEventStoppedFailed event) {
    final TaskInfo taskInfo = event.getTaskInfo();
    if (!haveAlertEmail(taskInfo)) {
      return;
    }
    String body = taskNotificationMessageGenerator(taskInfo.getTypeId()).failed(taskInfo, event.getFailureCause());
    log.trace("sending message {}", body);
    sendEmail("Task execution failure", taskInfo.getConfiguration().getAlertEmail(), body);
  }

  /**
   * Sends alert emails on completion.
   */
  @Subscribe
  @AllowConcurrentEvents
  public void on(final TaskEventStoppedDone event) {
    final TaskInfo taskInfo = event.getTaskInfo();
    if (!haveAlertEmail(taskInfo) ||
        taskInfo.getConfiguration().getNotificationCondition() == TaskNotificationCondition.FAILURE) {
      return;
    }
    String body = taskNotificationMessageGenerator(taskInfo.getTypeId()).completed(taskInfo);
    log.trace("sending message {}", body);
    sendEmail("Task execution completed", taskInfo.getConfiguration().getAlertEmail(), body);
  }

  private boolean haveAlertEmail(final TaskInfo taskInfo) {
    return taskInfo != null && taskInfo.getConfiguration().getAlertEmail() != null;
  }

  private void sendEmail(final String subject, final String address, final String body) {
    try {
      Email mail = new SimpleEmail();
      mail.setSubject(subject);
      mail.addTo(address);
      mail.setMsg(body);
      emailManager.get().send(mail);
    }
    catch (Exception e) {
      log.warn("Failed to send email", e);
    }
  }

  private TaskNotificationMessageGenerator taskNotificationMessageGenerator(final String typeId) {
    TaskNotificationMessageGenerator result = taskNotificationMessageGenerators.get(typeId);
    if (result == null) {
      result = taskNotificationMessageGenerators.get(DefaultTaskNotificationMessageGenerator.ID);
    }
    return result;
  }
}
