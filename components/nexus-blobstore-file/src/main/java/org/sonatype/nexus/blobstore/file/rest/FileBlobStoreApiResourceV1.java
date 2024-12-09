package org.sonatype.nexus.blobstore.file.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;

import static org.sonatype.nexus.blobstore.file.rest.FileBlobStoreApiResourceV1.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

/**
 * v1 endpoint for File BlobStore REST API
 *
 * @since 3.24
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class FileBlobStoreApiResourceV1
  extends FileBlobStoreResource
{
  public static final String RESOURCE_URI = V1_API_PREFIX + "/blobstores/file";

  @Inject
  public FileBlobStoreApiResourceV1(final BlobStoreManager blobStoreManager) {
    super(blobStoreManager);
  }
}
