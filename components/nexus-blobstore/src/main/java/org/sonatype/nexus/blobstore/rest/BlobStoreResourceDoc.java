package org.sonatype.nexus.blobstore.rest;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST facade for {@link BlobStoreResource}
 *
 * @since 3.14
 */
@Api(value = "Blob store")
public interface BlobStoreResourceDoc
{
  @ApiOperation("List the blob stores")
  List<GenericBlobStoreApiResponse> listBlobStores();

  @ApiOperation("Delete a blob store by name")
  void deleteBlobStore(@ApiParam("The name of the blob store to delete") String name) throws Exception;

  @ApiOperation("Get quota status for a given blob store")
  BlobStoreQuotaResultXO quotaStatus(String id);
}
