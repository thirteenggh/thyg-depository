package org.sonatype.nexus.script.plugin.internal.rest;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.rest.Resource;
import org.sonatype.nexus.script.plugin.internal.security.ScriptPrivilegeDescriptor;
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
public class ScriptPrivilegeApiResource
    extends PrivilegeApiResourceSupport
    implements Resource, ScriptPrivilegeApiResourceDoc
{
  @Inject
  public ScriptPrivilegeApiResource(final SecuritySystem securitySystem,
                                    final Map<String, PrivilegeDescriptor> privilegeDescriptors)
  {
    super(securitySystem, privilegeDescriptors);
  }

  @Override
  @POST
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:create")
  @Path("script")
  public Response createPrivilege(final ApiPrivilegeScriptRequest privilege) {
    return doCreate(ScriptPrivilegeDescriptor.TYPE, privilege);
  }

  @Override
  @PUT
  @RequiresAuthentication
  @RequiresPermissions("nexus:privileges:update")
  @Path("script/{privilegeId}")
  public void updatePrivilege(@PathParam("privilegeId") final String privilegeId,
                              final ApiPrivilegeScriptRequest privilege)
  {
    doUpdate(privilegeId, ScriptPrivilegeDescriptor.TYPE, privilege);
  }
}
