package org.sonatype.nexus.coreui.internal.atlas

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.common.atlas.SystemInformationGenerator
import org.sonatype.nexus.rest.Resource

import org.apache.shiro.authz.annotation.RequiresPermissions

import static com.google.common.base.Preconditions.checkNotNull

/**
 * Renders system information.
 *
 * This is required for download of the report from the UI.
 *
 * @since 2.7
 */
@Named
@Singleton
@Path(SystemInformationResource.RESOURCE_URI)
class SystemInformationResource
    extends ComponentSupport
    implements Resource
{
  public static final String RESOURCE_URI = '/atlas/system-information'

  private final SystemInformationGenerator systemInformationGenerator

  @Inject
  SystemInformationResource(final SystemInformationGenerator systemInformationGenerator) {
    this.systemInformationGenerator = checkNotNull(systemInformationGenerator)
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RequiresPermissions('nexus:atlas:read')
  Response report() {
    def report = systemInformationGenerator.report()

    // support downloading the json directly
    return Response.ok(report)
        .header('Content-Disposition', 'attachment; filename="sysinfo.json"')
        .build()
  }
}
