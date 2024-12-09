package org.sonatype.nexus.security.internal;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.security.role.Role;
import org.sonatype.nexus.security.role.RoleCreatedEvent;
import org.sonatype.nexus.security.role.RoleDeletedEvent;
import org.sonatype.nexus.security.role.RoleEvent;
import org.sonatype.nexus.security.role.RoleUpdatedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * {@link Role} auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class RoleAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "security.role";

  public RoleAuditor() {
    registerType(RoleCreatedEvent.class, CREATED_TYPE);
    registerType(RoleDeletedEvent.class, DELETED_TYPE);
    registerType(RoleUpdatedEvent.class, UPDATED_TYPE);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final RoleEvent event) {
    if (isRecording()) {
      Role role = event.getRole();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(type(event.getClass()));
      data.setContext(role.getRoleId());

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("id", role.getRoleId());
      attributes.put("name", role.getName());
      attributes.put("source", role.getSource());
      attributes.put("roles", string(role.getRoles()));
      attributes.put("privileges", string(role.getPrivileges()));

      record(data);
    }
  }
}
