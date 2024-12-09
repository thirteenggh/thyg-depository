package org.sonatype.nexus.blobstore.file.rest;

import javax.validation.Valid;

import org.sonatype.nexus.validation.Validate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST facade for {@link FileBlobStoreResource}
 *
 * @since 3.19
 */
@Api("Blob store")
public interface FileBlobStoreResourceDoc
{
  @ApiOperation("Create a file blob store")
  void createFileBlobStore(@Valid final FileBlobStoreApiCreateRequest request) throws Exception;

  @ApiOperation("Update a file blob store configuration by name")
  @Validate
  void updateFileBlobStore(
      @ApiParam("The name of the file blob store to update") final String name,
      @Valid final FileBlobStoreApiUpdateRequest request
  ) throws Exception;

  @ApiOperation("Get a file blob store configuration by name")
  FileBlobStoreApiModel getFileBlobStoreConfiguration(
      @ApiParam("The name of the file blob store to read") final String name
  );
}
