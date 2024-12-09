package org.sonatype.nexus.internal.support

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response
import javax.ws.rs.core.StreamingOutput

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.rest.Resource
import org.sonatype.nexus.supportzip.SupportZipGenerator

import org.apache.shiro.authz.annotation.RequiresAuthentication
import org.apache.shiro.authz.annotation.RequiresPermissions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import static javax.ws.rs.core.MediaType.APPLICATION_JSON
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM

/**
 * Resource for support API.
 *
 * @since 3.13
 */
@Named
@Singleton
@Path(SupportResource.RESOURCE_URI)
@Api('Support')
class SupportResource
    extends ComponentSupport
    implements Resource
{
  public static final String RESOURCE_URI = '/v1/support'

  @Inject
  SupportZipGenerator supportZipGenerator

  @RequiresAuthentication
  @RequiresPermissions('nexus:atlas:create')
  @ApiOperation('Creates and downloads a support zip')
  @Consumes([APPLICATION_JSON])
  @Produces([APPLICATION_OCTET_STREAM])
  @POST
  @Path("/supportzip")
  Response supportzip(final SupportZipGenerator.Request request) {
    def name = "support-${new Date().format('yyyyMMdd-HHmmss')}-1.zip"

    StreamingOutput entity = { OutputStream output ->
      supportZipGenerator.generate(request, 'support', output)
    }
    Response.ok(entity).header('Content-Disposition', "attachment; filename=\"$name\"").build()
  }

  @RequiresAuthentication
  @RequiresPermissions('nexus:atlas:create')
  @ApiOperation('Creates a support zip and returns the path')
  @Consumes([APPLICATION_JSON])
  @Produces([APPLICATION_JSON])
  @POST
  @Path("/supportzippath")
  SupportZipXO supportzippath(final SupportZipGenerator.Request request) {
    def result = supportZipGenerator.generate(request)
    return new SupportZipXO(
        file: result.localPath,
        name: result.filename,
        size: result.size,
        truncated: result.truncated
    )
  }
}
