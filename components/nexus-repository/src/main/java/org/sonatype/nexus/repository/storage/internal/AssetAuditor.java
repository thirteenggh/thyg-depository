package org.sonatype.nexus.repository.storage.internal;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetCreatedEvent;
import org.sonatype.nexus.repository.storage.AssetDeletedEvent;
import org.sonatype.nexus.repository.storage.AssetEvent;
import org.sonatype.nexus.repository.storage.AssetUpdatedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Repository asset auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class AssetAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "repository.asset";

  public AssetAuditor() {
    registerType(AssetCreatedEvent.class, CREATED_TYPE);
    registerType(AssetDeletedEvent.class, DELETED_TYPE);
    registerType(AssetUpdatedEvent.class, UPDATED_TYPE);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final AssetEvent event) {
    if (isRecording() && event.isLocal()) {
      Asset asset = event.getAsset();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(type(event.getClass()));
      data.setContext(asset.name());

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("repository.name", event.getRepositoryName());
      attributes.put("format", asset.format());
      attributes.put("name", asset.name());

      record(data);
    }
  }
}
