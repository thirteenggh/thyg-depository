package org.sonatype.nexus.blobstore.s3.rest.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.blobstore.s3.rest.internal.S3BlobStoreApiResourceBeta.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.BETA_API_PREFIX;

/**
 * beta endpoint for S3 BlobStore REST API
 *
 * @since 3.24
 * @deprecated moving to {@link S3BlobStoreApiResourceV1}
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class S3BlobStoreApiResourceBeta
  extends S3BlobStoreApiResource
{
  static final String RESOURCE_URI = BETA_API_PREFIX + "/blobstores/s3";

  @Inject
  public S3BlobStoreApiResourceBeta(final BlobStoreManager blobStoreManager,
                                    final S3BlobStoreApiUpdateValidation validation)
  {
    super(blobStoreManager, validation);
  }
}
