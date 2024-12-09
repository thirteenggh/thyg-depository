package org.sonatype.nexus.repository.content.store.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.event.asset.AssetAttributesEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetCreatedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetDeletedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetDownloadedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetKindEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetPurgedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetUpdatedEvent;
import org.sonatype.nexus.repository.content.event.asset.AssetUploadedEvent;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * Repository asset auditor.
 *
 * @since 3.27
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

    registerType(AssetPurgedEvent.class, PURGE_TYPE);

    registerType(AssetUpdatedEvent.class, UPDATED_TYPE);
    registerType(AssetAttributesEvent.class, UPDATED_TYPE + "-attribute");
    registerType(AssetDownloadedEvent.class, UPDATED_TYPE + "-downloaded");
    registerType(AssetKindEvent.class, UPDATED_TYPE + "-kind");
    registerType(AssetUploadedEvent.class, UPDATED_TYPE + "-uploaded");
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final AssetPurgedEvent event) {
    if (isRecording()) {
      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(type(event.getClass()));
      data.setContext(event.getRepository().getName());

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("repository.name", event.getRepository().getName());
      attributes.put("assetIds", Arrays.toString(event.getAssetIds()));

      record(data);
    }
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final AssetEvent event) {
    if (isRecording()) {
      Asset asset = event.getAsset();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(type(event.getClass()));
      data.setContext(asset.path());

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("repository.name", event.getRepository().getName());
      attributes.put("path", asset.path());
      attributes.put("kind", asset.kind());

      if (event instanceof AssetAttributesEvent){
        AssetAttributesEvent attributesEvent = (AssetAttributesEvent) event;

        attributes.put("attribute.changes", attributesEvent.getChanges()
            .stream().map(change -> {
              Map<String, Object> entry = new HashMap<>();
              entry.put("operation", change.getOperation());
              entry.put("key", change.getKey());
              entry.put("value", change.getValue());
              return entry;
            }).collect(Collectors.toList()));
      }

      record(data);
    }
  }
}
