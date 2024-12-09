package org.sonatype.nexus.audit.internal;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditDataRecordedEvent;
import org.sonatype.nexus.audit.AuditRecorder;
import org.sonatype.nexus.audit.InitiatorProvider;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.common.node.NodeAccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.logging.task.TaskLoggingMarkers.AUDIT_LOG_ONLY;

/**
 * Default {@link AuditRecorder} implementation.
 *
 * @since 3.1
 */
@Named
@Singleton
public class AuditRecorderImpl
    extends ComponentSupport
    implements AuditRecorder
{
  private final EventManager eventManager;

  private final NodeAccess nodeAccess;

  private final InitiatorProvider initiatorProvider;

  private final Logger auditLogger = LoggerFactory.getLogger("auditlog");

  private volatile boolean enabled = false;

  @Inject
  public AuditRecorderImpl(final EventManager eventManager,
                           final NodeAccess nodeAccess,
                           final InitiatorProvider initiatorProvider)
  {
    this.eventManager = eventManager;
    this.nodeAccess = nodeAccess;
    this.initiatorProvider = initiatorProvider;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public void record(final AuditData data) {
    checkNotNull(data);

    if (enabled) {
      // fill in timestamp, node-id and initiator if missing
      if (data.getTimestamp() == null) {
        data.setTimestamp(new Date());
      }
      if (data.getNodeId() == null) {
        data.setNodeId(nodeAccess.getId());
      }
      if (data.getInitiator() == null) {
        data.setInitiator(initiatorProvider.get());
      }

      try {
        auditLogger.info(AUDIT_LOG_ONLY, new AuditDTO(data).toString());

        eventManager.post(new AuditDataRecordedEvent(data));
      }
      catch (Exception e) {
        log.warn("Failed to record audit data", e);
      }
    }
  }
}
