package org.sonatype.nexus.repository.rest.api;

import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static org.sonatype.nexus.rest.ApiDocConstants.API_REPOSITORY_MANAGEMENT;
import static org.sonatype.nexus.rest.ApiDocConstants.AUTHENTICATION_REQUIRED;
import static org.sonatype.nexus.rest.ApiDocConstants.INSUFFICIENT_PERMISSIONS;
import static org.sonatype.nexus.rest.ApiDocConstants.REPOSITORY_DELETED;
import static org.sonatype.nexus.rest.ApiDocConstants.REPOSITORY_NOT_FOUND;

/**
 * @since 3.20
 */
@Api(value = API_REPOSITORY_MANAGEMENT)
public interface RepositoriesApiResourceDoc
{
  @ApiOperation("Delete repository of any format")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = REPOSITORY_DELETED),
      @ApiResponse(code = 401, message = AUTHENTICATION_REQUIRED),
      @ApiResponse(code = 403, message = INSUFFICIENT_PERMISSIONS),
      @ApiResponse(code = 404, message = REPOSITORY_NOT_FOUND)
  })
  Response deleteRepository(@ApiParam(value = "Name of the repository to delete") final String repositoryName)
      throws Exception;

  @ApiOperation("Schedule a 'Repair - Rebuild repository search' Task. Hosted or proxy repositories only.")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Repository search index rebuild has been scheduled"),
      @ApiResponse(code = 400, message = "Repository is not of hosted or proxy type"),
      @ApiResponse(code = 401, message = AUTHENTICATION_REQUIRED),
      @ApiResponse(code = 403, message = INSUFFICIENT_PERMISSIONS),
      @ApiResponse(code = 404, message = REPOSITORY_NOT_FOUND)
  })
  void rebuildIndex(@ApiParam(value = "Name of the repository to rebuild index") final String repositoryName);

  @ApiOperation("Invalidate repository cache. Proxy or group repositories only.")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Repository cache invalidated"),
      @ApiResponse(code = 400, message = "Repository is not of proxy or group type"),
      @ApiResponse(code = 401, message = AUTHENTICATION_REQUIRED),
      @ApiResponse(code = 403, message = INSUFFICIENT_PERMISSIONS),
      @ApiResponse(code = 404, message = REPOSITORY_NOT_FOUND)
  })
  void invalidateCache(@ApiParam(value = "Name of the repository to invalidate cache") final String repositoryName);
}
