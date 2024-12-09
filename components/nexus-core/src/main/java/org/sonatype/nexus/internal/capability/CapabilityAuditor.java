package org.sonatype.nexus.internal.capability;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.capability.Capability;
import org.sonatype.nexus.capability.CapabilityContext;
import org.sonatype.nexus.capability.CapabilityDescriptor;
import org.sonatype.nexus.capability.CapabilityEvent;
import org.sonatype.nexus.capability.CapabilityReference;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.formfields.Encrypted;
import org.sonatype.nexus.formfields.FormField;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * {@link Capability} auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class CapabilityAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "capability";

  public CapabilityAuditor() {
    registerType(CapabilityEvent.Created.class, CREATED_TYPE);
    registerType(CapabilityEvent.AfterActivated.class, "activated");
    registerType(CapabilityEvent.BeforePassivated.class, "passivated");
    registerType(CapabilityEvent.AfterRemove.class, DELETED_TYPE);
    registerType(CapabilityEvent.AfterUpdate.class, UPDATED_TYPE);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final CapabilityEvent event) {
    if (isRecording()) {
      CapabilityReference reference = event.getReference();
      CapabilityContext context = reference.context();
      CapabilityDescriptor descriptor = context.descriptor();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(type(event.getClass()));
      data.setContext(context.type().toString());

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("id", context.id().toString());
      attributes.put("type", context.type().toString());
      attributes.put("enabled", string(context.isEnabled()));
      attributes.put("active", string(context.isActive()));
      attributes.put("failed", string(context.hasFailure()));

      // include all non-secure properties
      Map<String,FormField> fields = fields(descriptor);
      for (Entry<String,String> entry : context.properties().entrySet()) {
        FormField field = fields.get(entry.getKey());
        // skip secure fields
        if (field instanceof Encrypted) {
          continue;
        }
        attributes.put("property." + entry.getKey(), entry.getValue());
      }

      record(data);
    }
  }

  private static Map<String, FormField> fields(final CapabilityDescriptor descriptor) {
    Map<String,FormField> result = new HashMap<>();
    for (FormField field : descriptor.formFields()) {
      result.put(field.getId(), field);
    }
    return result;
  }
}
