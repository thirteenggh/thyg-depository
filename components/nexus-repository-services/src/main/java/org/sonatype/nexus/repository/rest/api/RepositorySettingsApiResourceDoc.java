package org.sonatype.nexus.repository.rest.api;

import java.util.List;

import org.sonatype.nexus.repository.rest.api.model.AbstractApiRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static org.sonatype.nexus.rest.ApiDocConstants.API_REPOSITORY_MANAGEMENT;
import static org.sonatype.nexus.rest.ApiDocConstants.AUTHENTICATION_REQUIRED;
import static org.sonatype.nexus.rest.ApiDocConstants.INSUFFICIENT_PERMISSIONS;

/**
 * @since 3.26
 */
@Api(value = API_REPOSITORY_MANAGEMENT)
public interface RepositorySettingsApiResourceDoc
{
  @ApiOperation("List repositories")
  @ApiResponses(value = {
      @ApiResponse(code = 200,
          message = "Repositories list returned",
          response = AbstractApiRepository.class,
          responseContainer = "List"),
      @ApiResponse(code = 401, message = AUTHENTICATION_REQUIRED),
      @ApiResponse(code = 403, message = INSUFFICIENT_PERMISSIONS)
  })
  List<AbstractApiRepository> getRepositories();
}
