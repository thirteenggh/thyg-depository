package org.sonatype.nexus.security.privilege.rest;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.security.internal.rest.NexusSecurityApiConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @since 3.19
 */
@Api(value = "Security management: privileges")
public interface PrivilegeApiResourceDoc
{
  @ApiOperation("Retrieve a list of privileges.")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS)
  })
  Collection<ApiPrivilege> getPrivileges();

  @ApiOperation("Retrieve a privilege by id.")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS),
      @ApiResponse(code = 404, message = NexusSecurityApiConstants.PRIVILEGE_NOT_FOUND)
  })
  ApiPrivilege getPrivilege(@ApiParam("The id of the privilege to retrieve.") @NotNull final String id);

  @ApiOperation("Delete a privilege by id.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_READ_ONLY),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS),
      @ApiResponse(code = 404, message = NexusSecurityApiConstants.PRIVILEGE_NOT_FOUND)
  })
  void deletePrivilege(@ApiParam("The id of the privilege to delete.") @NotNull final String id);

  @ApiOperation("Create an application type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS)
  })
  Response createPrivilege(@ApiParam(
      "The privilege to create.") @NotNull @Valid final ApiPrivilegeApplicationRequest privilege);

  @ApiOperation("Update an application type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS),
      @ApiResponse(code = 404, message = NexusSecurityApiConstants.PRIVILEGE_NOT_FOUND)
  })
  void updatePrivilege(@ApiParam("The id of the privilege to update.") @NotNull final String id,
                       @ApiParam(
                           "The privilege to update.") @NotNull @Valid final ApiPrivilegeApplicationRequest privilege);

  @ApiOperation("Create a wildcard type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS)
  })
  Response createPrivilege(@ApiParam(
      "The privilege to create.") @NotNull @Valid final ApiPrivilegeWildcardRequest privilege);

  @ApiOperation("Update a wildcard type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS),
      @ApiResponse(code = 404, message = NexusSecurityApiConstants.PRIVILEGE_NOT_FOUND)
  })
  void updatePrivilege(@ApiParam("The id of the privilege to update.") @NotNull final String id,
                       @ApiParam(
                           "The privilege to update.") @NotNull @Valid final ApiPrivilegeWildcardRequest privilege);
}
