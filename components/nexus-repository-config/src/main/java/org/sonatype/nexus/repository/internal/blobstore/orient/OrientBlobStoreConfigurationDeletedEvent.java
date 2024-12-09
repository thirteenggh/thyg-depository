package org.sonatype.nexus.repository.internal.blobstore.orient;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.common.entity.EntityDeletedEvent;
import org.sonatype.nexus.common.entity.EntityMetadata;
import org.sonatype.nexus.repository.internal.blobstore.BlobStoreConfigurationDeletedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link BlobStoreConfiguration} deleted event.
 *
 * @since 3.1
 */
public class OrientBlobStoreConfigurationDeletedEvent
    extends EntityDeletedEvent
    implements BlobStoreConfigurationDeletedEvent
{
  private final String name;

  public OrientBlobStoreConfigurationDeletedEvent(final EntityMetadata metadata, final String name) {
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
