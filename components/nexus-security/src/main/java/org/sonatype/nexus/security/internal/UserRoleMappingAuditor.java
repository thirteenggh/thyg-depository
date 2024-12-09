package org.sonatype.nexus.security.internal;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.security.user.UserRoleMappingCreatedEvent;
import org.sonatype.nexus.security.user.UserRoleMappingDeletedEvent;
import org.sonatype.nexus.security.user.UserRoleMappingEvent;
import org.sonatype.nexus.security.user.UserRoleMappingUpdatedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * User role-mapping auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class UserRoleMappingAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "security.user-role-mapping";

  public UserRoleMappingAuditor() {
    registerType(UserRoleMappingCreatedEvent.class, CREATED_TYPE);
    registerType(UserRoleMappingDeletedEvent.class, DELETED_TYPE);
    registerType(UserRoleMappingUpdatedEvent.class, UPDATED_TYPE);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final UserRoleMappingEvent event) {
    if (isRecording()) {
      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(type(event.getClass()));
      data.setContext(event.getUserId());

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("id", event.getUserId());
      attributes.put("source", event.getUserSource());
      attributes.put("roles", string(event.getRoles()));

      record(data);
    }
  }
}
