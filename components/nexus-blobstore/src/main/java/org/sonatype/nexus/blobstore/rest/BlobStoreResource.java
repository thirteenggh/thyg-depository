package org.sonatype.nexus.blobstore.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaResult;
import org.sonatype.nexus.blobstore.quota.BlobStoreQuotaService;
import org.sonatype.nexus.rest.Resource;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Streams.stream;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * @since 3.14
 */
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class BlobStoreResource
    extends ComponentSupport
    implements Resource, BlobStoreResourceDoc
{
  private final BlobStoreManager blobStoreManager;

  private final BlobStoreQuotaService quotaService;

  public BlobStoreResource(final BlobStoreManager blobStoreManager, final BlobStoreQuotaService quotaService)
  {
    this.blobStoreManager = checkNotNull(blobStoreManager);
    this.quotaService = checkNotNull(quotaService);
  }

  @Override
  @RequiresAuthentication
  @RequiresPermissions("nexus:blobstores:read")
  @GET
  public List<GenericBlobStoreApiResponse> listBlobStores() {
    return stream(blobStoreManager.browse())
        .map(GenericBlobStoreApiResponse::new)
        .collect(toList());
  }

  @Override
  @RequiresAuthentication
  @RequiresPermissions("nexus:blobstores:delete")
  @DELETE
  @Path("/{name}")
  public void deleteBlobStore(@PathParam("name") final String name) throws Exception {
    if (!blobStoreManager.exists(name)) {
      BlobStoreResourceUtil.throwBlobStoreNotFoundException();
    }
    blobStoreManager.delete(name);
  }

  @RequiresAuthentication
  @RequiresPermissions("nexus:blobstores:read")
  @GET
  @Path("/{name}/quota-status")
  public BlobStoreQuotaResultXO quotaStatus(@PathParam("name") final String name) {
    BlobStore blobStore = blobStoreManager.get(name);

    if (blobStore == null) {
      throw new WebApplicationException(format("No blob store found for id '%s' ", name), NOT_FOUND);
    }

    BlobStoreQuotaResult result = quotaService.checkQuota(blobStore);

    return result != null ? BlobStoreQuotaResultXO.asQuotaXO(result) : BlobStoreQuotaResultXO.asNoQuotaXO(name);
  }
}
