package org.sonatype.nexus.repository.golang.rest;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.repository.golang.GolangFormat;
import org.sonatype.nexus.repository.golang.rest.model.GolangGroupRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.AbstractGroupRepositoriesApiResource;
import org.sonatype.nexus.validation.Validate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import static org.sonatype.nexus.rest.ApiDocConstants.API_REPOSITORY_MANAGEMENT;
import static org.sonatype.nexus.rest.ApiDocConstants.AUTHENTICATION_REQUIRED;
import static org.sonatype.nexus.rest.ApiDocConstants.DISABLED_IN_HIGH_AVAILABILITY;
import static org.sonatype.nexus.rest.ApiDocConstants.INSUFFICIENT_PERMISSIONS;
import static org.sonatype.nexus.rest.ApiDocConstants.REPOSITORY_CREATED;
import static org.sonatype.nexus.rest.ApiDocConstants.REPOSITORY_NOT_FOUND;
import static org.sonatype.nexus.rest.ApiDocConstants.REPOSITORY_UPDATED;

/**
 * REST resource for handling requests regarding group Golang repositories.
 *
 * @since 3.20
 */
@Api(value = API_REPOSITORY_MANAGEMENT)
public abstract class GolangGroupRepositoriesApiResource
    extends AbstractGroupRepositoriesApiResource<GolangGroupRepositoryApiRequest>
{
  @ApiOperation("Create a Go group repository")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = REPOSITORY_CREATED),
      @ApiResponse(code = 401, message = AUTHENTICATION_REQUIRED),
      @ApiResponse(code = 403, message = INSUFFICIENT_PERMISSIONS),
      @ApiResponse(code = 405, message = DISABLED_IN_HIGH_AVAILABILITY)
  })
  @POST
  @RequiresAuthentication
  @Validate
  @Override
  public Response createRepository(final GolangGroupRepositoryApiRequest request) {
    return super.createRepository(request);
  }

  @ApiOperation("Update a Go group repository")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = REPOSITORY_UPDATED),
      @ApiResponse(code = 401, message = AUTHENTICATION_REQUIRED),
      @ApiResponse(code = 403, message = INSUFFICIENT_PERMISSIONS),
      @ApiResponse(code = 404, message = REPOSITORY_NOT_FOUND)
  })
  @PUT
  @Path("/{repositoryName}")
  @RequiresAuthentication
  @Validate
  @Override
  public Response updateRepository(
      final GolangGroupRepositoryApiRequest request,
      @ApiParam(value = "Name of the repository to update") @PathParam("repositoryName") final String repositoryName)
  {
    return super.updateRepository(request, repositoryName);
  }

  @Override
  public boolean isApiEnabled() {
    return highAvailabilitySupportChecker.isSupported(GolangFormat.NAME);
  }
}
