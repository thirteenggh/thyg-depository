package org.sonatype.nexus.internal.email;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.email.EmailConfiguration;
import org.sonatype.nexus.email.EmailConfigurationChangedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Email auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class EmailAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "email";

  @Subscribe
  @AllowConcurrentEvents
  public void on(final EmailConfigurationChangedEvent event) {
    if (isRecording()) {
      EmailConfiguration configuration = event.getConfiguration();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(CHANGED_TYPE);
      data.setContext(SYSTEM_CONTEXT);

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("enabled", string(configuration.isEnabled()));
      attributes.put("host", configuration.getHost());
      attributes.put("port", string(configuration.getPort()));
      attributes.put("username", configuration.getUsername());
      attributes.put("fromAddress", configuration.getFromAddress());
      attributes.put("subjectPrefix", configuration.getSubjectPrefix());

      // TODO: various ssl/tls/trust-store shit

      record(data);
    }
  }
}
