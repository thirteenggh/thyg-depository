package org.sonatype.nexus.blobstore.s3.rest.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;

import static org.sonatype.nexus.blobstore.s3.rest.internal.S3BlobStoreApiResourceV1.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

/**
 * v1 endpoint for S3 BlobStore REST API
 *
 * @since 3.24
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class S3BlobStoreApiResourceV1
  extends S3BlobStoreApiResource
{
  static final String RESOURCE_URI = V1_API_PREFIX + "/blobstores/s3";

  @Inject
  public S3BlobStoreApiResourceV1(final BlobStoreManager blobStoreManager,
                                  final S3BlobStoreApiUpdateValidation validation)
  {
    super(blobStoreManager, validation);
  }
}
