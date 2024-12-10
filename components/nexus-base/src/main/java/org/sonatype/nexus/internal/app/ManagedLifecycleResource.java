package org.sonatype.nexus.internal.app;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ManagedLifecycle.Phase;
import org.sonatype.nexus.common.app.ManagedLifecycleManager;
import org.sonatype.nexus.rest.Resource;
import org.sonatype.nexus.rest.WebApplicationMessageException;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

@Path(ManagedLifecycleResource.RESOURCE_URI)
@Named
@Singleton
public class ManagedLifecycleResource
    extends ComponentSupport
    implements Resource, ManagedLifecycleResourceDoc
{
  public static final String RESOURCE_URI = V1_API_PREFIX + "/lifecycle";

  private final ManagedLifecycleManager lifecycleManager;

  @Inject
  public ManagedLifecycleResource(final ManagedLifecycleManager lifecycleManager) {
    this.lifecycleManager = checkNotNull(lifecycleManager);
  }

  @GET
  @Path("phase")
  @Produces(TEXT_PLAIN)
  @Override
  public String getPhase() {
    return lifecycleManager.getCurrentPhase().name();
  }

  @PUT
  @Path("phase")
  @Consumes(TEXT_PLAIN)
  @RequiresAuthentication
  @RequiresPermissions("nexus:*")
  @Override
  public void setPhase(final String phase) {
    try {
      lifecycleManager.to(Phase.valueOf(phase));
    }
    catch (Exception e) {
      log.warn("Problem moving to phase {}", phase, e);
      throw new WebApplicationMessageException(INTERNAL_SERVER_ERROR, "Problem moving to phase " + phase + ": " + e);
    }
  }

  @PUT
  @Path("bounce")
  @Consumes(TEXT_PLAIN)
  @RequiresAuthentication
  @RequiresPermissions("nexus:*")
  @Override
  public void bounce(final String phase) {
    try {
      lifecycleManager.bounce(Phase.valueOf(phase));
    }
    catch (Exception e) {
      log.warn("Problem bouncing phase {}", phase, e);
      throw new WebApplicationMessageException(INTERNAL_SERVER_ERROR, "Problem bouncing phase " + phase + ": " + e);
    }
  }
}
