package org.sonatype.nexus.security.role.rest;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.sonatype.nexus.security.authz.NoSuchAuthorizationManagerException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.validator.constraints.NotEmpty;

import static org.sonatype.nexus.security.user.UserManager.DEFAULT_SOURCE;


/**
 * Swagger documentation for {@link RoleApiResource}
 *
 * @since 3.19
 */
@Api(value = "Security management: roles")
public interface RoleApiResourceDoc
{
  @ApiOperation("List roles")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "The specified source does not exist"),
      @ApiResponse(code = 403, message = "Insufficient permissions to read roles")
  })
  List<RoleXOResponse> getRoles(
      @ApiParam(value = "The id of the user source to filter the roles by, if supplied. Otherwise roles from all user sources will be returned.")
      final String source
  );

  @ApiOperation("Create role")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = "Insufficient permissions to create role")
  })
  RoleXOResponse create(
      @ApiParam(value = "A role configuration", required = true)
      @NotNull @Valid final RoleXORequest roleXO

  ) throws NoSuchAuthorizationManagerException;

  @ApiOperation("Get role")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "The specified source does not exist"),
      @ApiResponse(code = 403, message = "Insufficient permissions to read roles"),
      @ApiResponse(code = 404, message = "Role not found")
  })
  RoleXOResponse getRole(
      @ApiParam(value = "The id of the user source to filter the roles by. Available sources can be fetched using the 'User Sources' endpoint.", defaultValue = DEFAULT_SOURCE)
      final String source,
      @ApiParam(value = "The id of the role to get", required = true) @NotEmpty final String id
  );

  @ApiOperation("Update role")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = "Insufficient permissions to update role"),
      @ApiResponse(code = 404, message = "Role not found")
  })
  void update(
      @ApiParam(value = "The id of the role to update", required = true) @NotEmpty final String id,
      @ApiParam(value = "A role configuration", required = true)
      @NotNull @Valid final RoleXORequest roleXO
  ) throws NoSuchAuthorizationManagerException;

  @ApiOperation("Delete role")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = "Insufficient permissions to delete role"),
      @ApiResponse(code = 404, message = "Role not found")
  })
  void delete(
      @ApiParam(value = "The id of the role to delete", required = true) @NotEmpty final String id
  ) throws NoSuchAuthorizationManagerException;
}
