package org.sonatype.nexus.repository.internal.blobstore.orient;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.common.entity.EntityCreatedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.repository.internal.blobstore.BlobStoreConfigurationCreatedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link BlobStoreConfiguration} created event.
 *
 * @since 3.1
 */
public class OrientBlobStoreConfigurationCreatedEvent
    extends EntityCreatedEvent
    implements BlobStoreConfigurationCreatedEvent
{
  private final String name;

  public OrientBlobStoreConfigurationCreatedEvent(final EntityMetadata metadata, final String name) {
    super(metadata);
    this.name = checkNotNull(name);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public BlobStoreConfiguration getConfiguration() {
    return getEntity();
  }
}
