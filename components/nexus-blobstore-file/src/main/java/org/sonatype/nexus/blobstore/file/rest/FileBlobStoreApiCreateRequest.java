package org.sonatype.nexus.blobstore.file.rest;

import javax.validation.constraints.NotEmpty;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;

/**
 * @since 3.19
 */
public class FileBlobStoreApiCreateRequest
    extends FileBlobStoreApiModel
{
  @NotEmpty(message = "Name is required")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public BlobStoreConfiguration toBlobStoreConfiguration(final BlobStoreConfiguration oldConfig) {
    BlobStoreConfiguration newConfig = super.toBlobStoreConfiguration(oldConfig);
    newConfig.setName(name);
    return newConfig;
  }
}
