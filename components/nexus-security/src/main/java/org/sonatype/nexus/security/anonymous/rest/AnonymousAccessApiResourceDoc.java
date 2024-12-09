package org.sonatype.nexus.security.anonymous.rest;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @since 3.24
 */
@Api(value = "Security Management: Anonymous Access")
public interface AnonymousAccessApiResourceDoc
{
  @ApiOperation("Get Anonymous Access settings")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = "Insufficient permissions to update settings")
  })
  AnonymousAccessSettingsXO read();

  @ApiOperation("Update Anonymous Access settings")
  @ApiResponses(value = {
      @ApiResponse(code = 403, message = "Insufficient permissions to update settings")
  })
  AnonymousAccessSettingsXO update(@Valid AnonymousAccessSettingsXO anonymousXO);
}
