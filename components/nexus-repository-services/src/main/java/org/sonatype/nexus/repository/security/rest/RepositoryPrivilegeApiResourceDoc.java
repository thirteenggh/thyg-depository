package org.sonatype.nexus.repository.security.rest;

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
public interface RepositoryPrivilegeApiResourceDoc
{
  @ApiOperation("Create a repository admin type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS)
  })
  Response createPrivilege(@ApiParam(
      "The privilege to create.") @NotNull @Valid final ApiPrivilegeRepositoryAdminRequest privilege);

  @ApiOperation("Update a repository admin type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS),
      @ApiResponse(code = 404, message = NexusSecurityApiConstants.PRIVILEGE_NOT_FOUND)
  })
  void updatePrivilege(@ApiParam("The id of the privilege to update.") @NotNull final String id,
                       @ApiParam(
                           "The privilege to update.") @NotNull @Valid final ApiPrivilegeRepositoryAdminRequest privilege);

  @ApiOperation("Create a repository view type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS)
  })
  Response createPrivilege(@ApiParam(
      "The privilege to create.") @NotNull @Valid final ApiPrivilegeRepositoryViewRequest privilege);

  @ApiOperation("Update a repository view type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS),
      @ApiResponse(code = 404, message = NexusSecurityApiConstants.PRIVILEGE_NOT_FOUND)
  })
  void updatePrivilege(@ApiParam("The id of the privilege to update.") @NotNull final String id,
                       @ApiParam(
                           "The privilege to update.") @NotNull @Valid final ApiPrivilegeRepositoryViewRequest privilege);

  @ApiOperation("Create a repository content selector type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS)
  })
  Response createPrivilege(@ApiParam(
      "The privilege to create.") @NotNull @Valid final ApiPrivilegeRepositoryContentSelectorRequest privilege);

  @ApiOperation("Update a repository content selector type privilege.")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = NexusSecurityApiConstants.PRIVILEGE_MISCONFIGURED),
      @ApiResponse(code = 403, message = NexusSecurityApiConstants.INVALID_PERMISSIONS),
      @ApiResponse(code = 404, message = NexusSecurityApiConstants.PRIVILEGE_NOT_FOUND)
  })
  void updatePrivilege(@ApiParam("The id of the privilege to update.") @NotNull final String id,
                       @ApiParam(
                           "The privilege to update.") @NotNull @Valid final ApiPrivilegeRepositoryContentSelectorRequest privilege);
}
