package org.sonatype.nexus.repository.storage.internal;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.ComponentCreatedEvent;
import org.sonatype.nexus.repository.storage.ComponentDeletedEvent;
import org.sonatype.nexus.repository.storage.ComponentEvent;
import org.sonatype.nexus.repository.storage.ComponentUpdatedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Repository component auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class ComponentAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "repository.component";

  public ComponentAuditor() {
    registerType(ComponentCreatedEvent.class, CREATED_TYPE);
    registerType(ComponentDeletedEvent.class, DELETED_TYPE);
    registerType(ComponentUpdatedEvent.class, UPDATED_TYPE);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final ComponentEvent event) {
    if (isRecording() && event.isLocal()) {
      Component component = event.getComponent();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(type(event.getClass()));
      data.setContext(component.name());

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("repository.name", event.getRepositoryName());
      attributes.put("format", component.format());
      attributes.put("name", component.name());
      attributes.put("group", component.group());
      attributes.put("version", component.version());

      record(data);
    }
  }
}
