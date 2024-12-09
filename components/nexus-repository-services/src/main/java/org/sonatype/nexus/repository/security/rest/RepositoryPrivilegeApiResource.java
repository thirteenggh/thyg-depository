package org.sonatype.nexus.repository.security.rest;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.repository.security.RepositoryAdminPrivilegeDescriptor;
import org.sonatype.nexus.repository.security.RepositoryContentSelectorPrivilegeDescriptor;
import org.sonatype.nexus.repository.security.RepositoryViewPrivilegeDescriptor;
import org.sonatype.nexus.rest.Resource;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.privilege.PrivilegeDescriptor;
import org.sonatype.nexus.security.privilege.rest.PrivilegeApiResourceSupport;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @since 3.19
 */
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class RepositoryPrivilegeApiResource
    extends PrivilegeApiResourceSupport
    implements Resource, RepositoryPrivilegeApiResourceDoc
{
  @Inject
  public RepositoryPrivilegeApiResource(final SecuritySystem securitySystem,
                                        final Map<String, PrivilegeDescriptor> privilegeDescriptors)
  {
    super(securitySystem, privilegeDescriptors);
  }

  @Override
  @POST
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:create")
  @Path("repository-admin")
  public Response createPrivilege(final ApiPrivilegeRepositoryAdminRequest privilege) {
    return doCreate(RepositoryAdminPrivilegeDescriptor.TYPE, privilege);
  }

  @Override
  @PUT
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:update")
  @Path("repository-admin/{privilegeId}")
  public void updatePrivilege(@PathParam("privilegeId") final String privilegeId,
                              final ApiPrivilegeRepositoryAdminRequest privilege)
  {
    doUpdate(privilegeId, RepositoryAdminPrivilegeDescriptor.TYPE, privilege);
  }

  @Override
  @POST
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:create")
  @Path("repository-view")
  public Response createPrivilege(final ApiPrivilegeRepositoryViewRequest privilege) {
    return doCreate(RepositoryViewPrivilegeDescriptor.TYPE, privilege);
  }

  @Override
  @PUT
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:update")
  @Path("repository-view/{privilegeId}")
  public void updatePrivilege(@PathParam("privilegeId") final String privilegeId,
                              final ApiPrivilegeRepositoryViewRequest privilege)
  {
    doUpdate(privilegeId, RepositoryViewPrivilegeDescriptor.TYPE, privilege);
  }

  @Override
  @POST
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:create")
  @Path("repository-content-selector")
  public Response createPrivilege(final ApiPrivilegeRepositoryContentSelectorRequest privilege) {
    return doCreate(RepositoryContentSelectorPrivilegeDescriptor.TYPE, privilege);
  }

  @Override
  @PUT
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:update")
  @Path("repository-content-selector/{privilegeId}")
  public void updatePrivilege(@PathParam("privilegeId") final String privilegeId,
                              final ApiPrivilegeRepositoryContentSelectorRequest privilege)
  {
    doUpdate(privilegeId, RepositoryContentSelectorPrivilegeDescriptor.TYPE, privilege);
  }
}
