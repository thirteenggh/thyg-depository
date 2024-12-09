package org.sonatype.nexus.internal.log;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.log.LoggerLevel;
import org.sonatype.nexus.common.log.LoggerLevelChangedEvent;
import org.sonatype.nexus.common.log.LoggersResetEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Logging auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class LoggingAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "logging";

  @Subscribe
  @AllowConcurrentEvents
  public void on(final LoggersResetEvent event) {
    if (isRecording()) {
      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType("reset");
      data.setContext(SYSTEM_CONTEXT);
      record(data);
    }
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final LoggerLevelChangedEvent event) {
    if (isRecording()) {
      String logger = event.getLogger();
      LoggerLevel level = event.getLevel();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(CHANGED_TYPE);
      data.setContext(logger);

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("logger", logger);
      attributes.put("level", string(level));
      record(data);
    }
  }
}
