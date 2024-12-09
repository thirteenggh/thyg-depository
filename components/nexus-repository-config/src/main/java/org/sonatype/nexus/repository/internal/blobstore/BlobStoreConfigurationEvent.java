package org.sonatype.nexus.repository.internal.blobstore;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;

/**
 * {@link BlobStoreConfiguration} event.
 *
 * @since 3.1
 */
public interface BlobStoreConfigurationEvent
{
  boolean isLocal();

  String getName();

  BlobStoreConfiguration getConfiguration();
}
