package org.sonatype.nexus.security.privilege.rest;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.sonatype.nexus.rest.Resource;
import org.sonatype.nexus.rest.WebApplicationMessageException;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.privilege.ApplicationPrivilegeDescriptor;
import org.sonatype.nexus.security.privilege.NoSuchPrivilegeException;
import org.sonatype.nexus.security.privilege.PrivilegeDescriptor;
import org.sonatype.nexus.security.privilege.ReadonlyPrivilegeException;
import org.sonatype.nexus.security.privilege.WildcardPrivilegeDescriptor;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @since 3.19
 */
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class PrivilegeApiResource
    extends PrivilegeApiResourceSupport
    implements Resource, PrivilegeApiResourceDoc
{
  @Inject
  public PrivilegeApiResource(final SecuritySystem securitySystem,
                              final Map<String, PrivilegeDescriptor> privilegeDescriptors)
  {
    super(securitySystem, privilegeDescriptors);
  }

  @Override
  @GET
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:read")
  public List<ApiPrivilege> getPrivileges() {
    return getSecuritySystem().listPrivileges().stream().map(this::toApiPrivilege)
        .sorted(Comparator.comparing(ApiPrivilege::getName)).collect(Collectors.toList());
  }

  @Override
  @GET
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:read")
  @Path("{privilegeId}")
  public ApiPrivilege getPrivilege(@PathParam("privilegeId") final String privilegeId) {
    try {
      return toApiPrivilege(getDefaultAuthorizationManager().getPrivilege(privilegeId));
    }
    catch (NoSuchPrivilegeException e) {
      log.debug("Attempt to retrieve privilege '{}' failed, as it wasn't found in the system.", privilegeId, e);
      throw new WebApplicationMessageException(Status.NOT_FOUND, String.format(PRIV_NOT_FOUND, privilegeId),
          MediaType.APPLICATION_JSON);
    }
  }

  @Override
  @DELETE
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:delete")
  @Path("{privilegeId}")
  public void deletePrivilege(@PathParam("privilegeId") final String privilegeId) {
    try {
      getDefaultAuthorizationManager().deletePrivilege(privilegeId);
    }
    catch (NoSuchPrivilegeException e) {
      log.debug("Attempt to delete privilege '{}' failed, as it wasn't found in the system.", privilegeId, e);
      throw new WebApplicationMessageException(Status.NOT_FOUND, String.format(PRIV_NOT_FOUND, privilegeId),
          MediaType.APPLICATION_JSON);
    }
    catch (ReadonlyPrivilegeException e) {
      log.debug("Attempt to delete privilege '{}' failed, as it is readonly.", privilegeId, e);
      throw new WebApplicationMessageException(Status.BAD_REQUEST, String.format(PRIV_INTERNAL, privilegeId),
          MediaType.APPLICATION_JSON);
    }
  }

  @Override
  @POST
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:create")
  @Path("application")
  public Response createPrivilege(final ApiPrivilegeApplicationRequest privilege) {
    return doCreate(ApplicationPrivilegeDescriptor.TYPE, privilege);
  }

  @Override
  @PUT
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:update")
  @Path("application/{privilegeId}")
  public void updatePrivilege(@PathParam("privilegeId") final String privilegeId,
                              final ApiPrivilegeApplicationRequest privilege)
  {
    doUpdate(privilegeId, ApplicationPrivilegeDescriptor.TYPE, privilege);
  }

  @Override
  @POST
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:create")
  @Path("wildcard")
  public Response createPrivilege(final ApiPrivilegeWildcardRequest privilege) {
    return doCreate(WildcardPrivilegeDescriptor.TYPE, privilege);
  }

  @Override
  @PUT
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:update")
  @Path("wildcard/{privilegeId}")
  public void updatePrivilege(@PathParam("privilegeId") final String privilegeId,
                              final ApiPrivilegeWildcardRequest privilege)
  {
    doUpdate(privilegeId, WildcardPrivilegeDescriptor.TYPE, privilege);
  }
}
