package org.sonatype.nexus.repository.internal.blobstore;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.audit.AuditData;
import org.sonatype.nexus.audit.AuditorSupport;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.api.BlobStoreCreatedEvent;
import org.sonatype.nexus.blobstore.api.BlobStoreDeletedEvent;
import org.sonatype.nexus.blobstore.api.BlobStoreEvent;
import org.sonatype.nexus.blobstore.api.BlobStoreUpdatedEvent;
import org.sonatype.nexus.common.event.EventAware;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * {@link BlobStore} auditor.
 *
 * @since 3.1
 */
@Named
@Singleton
public class BlobStoreAuditor
    extends AuditorSupport
    implements EventAware
{
  public static final String DOMAIN = "blobstore";

  public BlobStoreAuditor() {
    registerType(BlobStoreCreatedEvent.class, CREATED_TYPE);
    registerType(BlobStoreUpdatedEvent.class, UPDATED_TYPE);
    registerType(BlobStoreDeletedEvent.class, DELETED_TYPE);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final BlobStoreEvent event) {
    if (isRecording()) {
      BlobStore blobStore = event.getBlobStore();
      BlobStoreConfiguration configuration = blobStore.getBlobStoreConfiguration();

      AuditData data = new AuditData();
      data.setDomain(DOMAIN);
      data.setType(type(event.getClass()));
      data.setContext(configuration.getName());

      Map<String, Object> attributes = data.getAttributes();
      attributes.put("name", configuration.getName());
      attributes.put("type", configuration.getType());

      record(data);
    }
  }
}
