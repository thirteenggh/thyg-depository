package org.sonatype.nexus.security.internal.rest;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Swagger documentation for {@link SecurityApiResource}
 *
 * @since 3.17
 */
@Api(value = "Security management")
public interface SecurityApiResourceDoc
{
  @ApiOperation("Retrieve a list of the available user sources.")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS)
  })
  List<ApiUserSource> getUserSources();
}
