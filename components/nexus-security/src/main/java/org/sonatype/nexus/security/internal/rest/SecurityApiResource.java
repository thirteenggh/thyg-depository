
package org.sonatype.nexus.security.internal.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.rest.Resource;
import org.sonatype.nexus.security.user.ConfiguredUsersUserManager;
import org.sonatype.nexus.security.user.UserManager;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @since 3.17
 */
@RequiresAuthentication
@Produces(MediaType.APPLICATION_JSON)
public class SecurityApiResource
    extends ComponentSupport
    implements Resource, SecurityApiResourceDoc
{
  private final Map<String, UserManager> userManagers;

  @Inject
  public SecurityApiResource(final Map<String, UserManager> userManagers) {
    this.userManagers = userManagers;
  }

  @Override
  @GET
  @Path("user-sources")
  @RequiresPermissions("nexus:users:read")
  public List<ApiUserSource> getUserSources() {
    return userManagers.values().stream().filter(um -> !ConfiguredUsersUserManager.SOURCE.equals(um.getSource()))
        .map(um -> new ApiUserSource(um)).collect(Collectors.toList());
  }
}
