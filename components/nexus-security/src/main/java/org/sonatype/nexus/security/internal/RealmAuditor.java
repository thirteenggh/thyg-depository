package org.sonatype.nexus.security.internal;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.security.realm.RealmConfigurationChangedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Realm auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class RealmAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "security.realm";

  @Subscribe
  @AllowConcurrentEvents
  public void on(final RealmConfigurationChangedEvent event) {
    if (isRecording()) {
      RealmConfiguration configuration = event.getConfiguration();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(CHANGED_TYPE);
      data.setContext(SYSTEM_CONTEXT);

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("realms", string(configuration.getRealmNames()));

      record(data);
    }
  }
}
