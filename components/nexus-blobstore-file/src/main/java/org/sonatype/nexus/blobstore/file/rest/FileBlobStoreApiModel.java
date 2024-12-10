package org.sonatype.nexus.blobstore.file.rest;

import javax.validation.constraints.NotEmpty;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.file.FileBlobStore;
import org.sonatype.nexus.blobstore.rest.BlobStoreApiModel;

import io.swagger.annotations.ApiModelProperty;

public class FileBlobStoreApiModel
    extends BlobStoreApiModel
{
  @ApiModelProperty(
      "The path to the blobstore contents. This can be an absolute path to anywhere on the system Trust Repository Manager " +
          "has access to or it can be a path relative to the sonatype-work directory."
  )
  @NotEmpty(message = "Path is required")
  private String path;

  public FileBlobStoreApiModel() {
    super();
  }

  public FileBlobStoreApiModel(final BlobStoreConfiguration configuration) {
    super(configuration);
    this.path = configuration.attributes(FileBlobStore.CONFIG_KEY).get(FileBlobStore.PATH_KEY, String.class);
  }

  public String getPath() {
    return path;
  }

  public void setPath(final String path) {
    this.path = path;
  }

  @Override
  public BlobStoreConfiguration toBlobStoreConfiguration(final BlobStoreConfiguration configuration) {
    BlobStoreConfiguration newConfig = super.toBlobStoreConfiguration(configuration);
    newConfig.setType(FileBlobStore.TYPE);
    newConfig.attributes(FileBlobStore.CONFIG_KEY).set(FileBlobStore.PATH_KEY, path);
    return newConfig;
  }
}
