package org.sonatype.nexus.coreui.internal.wonderland;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ApplicationVersion;
import org.sonatype.nexus.rest.Resource;

import org.apache.shiro.authz.annotation.RequiresUser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Status resource.
 *
 * @since 3.0
 */
@Named
@Singleton
@Path(StatusResource.RESOURCE_URI)
public class StatusResource
    extends ComponentSupport
    implements Resource
{
  public static final String RESOURCE_URI = "/wonderland/status";

  private final ApplicationVersion applicationVersion;

  @Inject
  public StatusResource(final ApplicationVersion applicationVersion) {
    this.applicationVersion = checkNotNull(applicationVersion);
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  @RequiresUser
  public StatusXO get() {
    StatusXO result = new StatusXO();
    result.setVersion(applicationVersion.getVersion());
    result.setEdition(applicationVersion.getEdition());
    return result;
  }
}
