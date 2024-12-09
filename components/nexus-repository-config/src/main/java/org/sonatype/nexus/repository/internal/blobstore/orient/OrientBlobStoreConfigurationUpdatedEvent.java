package org.sonatype.nexus.repository.internal.blobstore.orient;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.common.entity.EntityUpdatedEvent;
import org.sonatype.nexus.repository.internal.blobstore.BlobStoreConfigurationUpdatedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link BlobStoreConfiguration} updated event.
 *
 * @since 3.14
 */
public class OrientBlobStoreConfigurationUpdatedEvent
    extends EntityUpdatedEvent
    implements BlobStoreConfigurationUpdatedEvent
{
  private final String name;

  public OrientBlobStoreConfigurationUpdatedEvent(final EntityMetadata metadata, final String name) {
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
