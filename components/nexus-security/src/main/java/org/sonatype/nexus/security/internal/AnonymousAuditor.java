package org.sonatype.nexus.security.internal;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;
import org.sonatype.nexus.security.anonymous.AnonymousConfigurationChangedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Anonymous auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class AnonymousAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "security.anonymous";

  @Subscribe
  @AllowConcurrentEvents
  public void on(final AnonymousConfigurationChangedEvent event) {
    if (isRecording()) {
      AnonymousConfiguration configuration = event.getConfiguration();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(CHANGED_TYPE);
      data.setContext(SYSTEM_CONTEXT);

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("enabled", string(configuration.isEnabled()));
      attributes.put("userId", configuration.getUserId());
      attributes.put("realm", configuration.getRealmName());

      record(data);
    }
  }
}
