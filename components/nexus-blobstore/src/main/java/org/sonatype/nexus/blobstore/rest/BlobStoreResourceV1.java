package org.sonatype.nexus.blobstore.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaService;

import static org.sonatype.nexus.blobstore.rest.BlobStoreResourceV1.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

/**
 * v1 endpoint for BlobStore REST API
 *
 * @since 3.24
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class BlobStoreResourceV1
  extends BlobStoreResource
{
  static final String RESOURCE_URI = V1_API_PREFIX + "/blobstores";

  @Inject
  public BlobStoreResourceV1(final BlobStoreManager blobStoreManager,
                             final BlobStoreQuotaService quotaService)
  {
    super(blobStoreManager, quotaService);
  }
}
