package org.sonatype.repository.helm.rest;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.repository.rest.api.AbstractProxyRepositoriesApiResource;
import org.sonatype.nexus.validation.Validate;
import org.sonatype.repository.helm.api.HelmProxyRepositoryApiRequest;
import org.sonatype.repository.helm.internal.HelmFormat;

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
import static org.sonatype.nexus.rest.ApiDocConstants.REPOSITORY_UPDATED;

/**
 * @since 3.28
 */
@Api(value = API_REPOSITORY_MANAGEMENT)
public abstract class HelmProxyRepositoriesApiResource
    extends AbstractProxyRepositoriesApiResource<HelmProxyRepositoryApiRequest>
{
  @ApiOperation("Create Helm proxy repository")
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
  public Response createRepository(final HelmProxyRepositoryApiRequest request) {
    return super.createRepository(request);
  }

  @ApiOperation("Update Helm proxy repository")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = REPOSITORY_UPDATED),
      @ApiResponse(code = 401, message = AUTHENTICATION_REQUIRED),
      @ApiResponse(code = 403, message = INSUFFICIENT_PERMISSIONS)
  })
  @PUT
  @Path("/{repositoryName}")
  @RequiresAuthentication
  @Validate
  @Override
  public Response updateRepository(
      final HelmProxyRepositoryApiRequest request,
      @ApiParam(value = "Name of the repository to update") @PathParam("repositoryName") final String repositoryName)
  {
    return super.updateRepository(request, repositoryName);
  }

  @Override
  public boolean isApiEnabled() {
    return highAvailabilitySupportChecker.isSupported(HelmFormat.NAME);
  }
}
