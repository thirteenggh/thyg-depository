package org.sonatype.nexus.blobstore.file.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.blobstore.file.rest.FileBlobStoreApiResourceBeta.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.BETA_API_PREFIX;

/**
 * beta endpoint for File BlobStore REST API
 *
 * @since 3.24
 * @deprecated moving to {@link FileBlobStoreApiResourceV1}
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class FileBlobStoreApiResourceBeta
  extends FileBlobStoreResource
{
  static final String RESOURCE_URI = BETA_API_PREFIX + "/blobstores/file";

  @Inject
  public FileBlobStoreApiResourceBeta(final BlobStoreManager blobStoreManager) {
    super(blobStoreManager);
  }
}
