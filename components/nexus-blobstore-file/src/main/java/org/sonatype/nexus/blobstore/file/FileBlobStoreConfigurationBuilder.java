package org.sonatype.nexus.blobstore.file;

import java.util.function.Supplier;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A builder to configure a file blob store.
 *
 * @since 3.1
 */
public class FileBlobStoreConfigurationBuilder
{
  private final Supplier<BlobStoreConfiguration> configurationSupplier;

  private final String name;

  private String path;

  /**
   * Creates a new builder using the specified name for the resulting blob store. Unless customized, the name is also
   * used as the path for the blob store.
   */
  public FileBlobStoreConfigurationBuilder(final String name,
                                           final Supplier<BlobStoreConfiguration> configurationSupplier)
  {
    this.name = checkNotNull(name);
    this.configurationSupplier = checkNotNull(configurationSupplier);
    this.path = name;
  }

  /**
   * Sets the local path where the blobs are persisted.
   */
  public FileBlobStoreConfigurationBuilder path(final String path) {
    this.path = checkNotNull(path);
    return this;
  }

  /**
   * Creates the configuration for the desired file blob store.
   */
  public BlobStoreConfiguration build() {
    final BlobStoreConfiguration configuration = configurationSupplier.get();
    configuration.setName(name);
    configuration.setType(FileBlobStore.TYPE);
    configuration.attributes(FileBlobStore.CONFIG_KEY).set(FileBlobStore.PATH_KEY, path);
    return configuration;
  }
}
