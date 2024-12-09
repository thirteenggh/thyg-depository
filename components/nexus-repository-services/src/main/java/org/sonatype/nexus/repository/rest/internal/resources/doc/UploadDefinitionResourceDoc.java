package org.sonatype.nexus.repository.rest.internal.resources.doc;

import java.util.List;

import org.sonatype.nexus.repository.rest.api.UploadDefinitionXO;
import org.sonatype.nexus.repository.rest.internal.resources.UploadDefinitionResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Swagger documentation for {@link UploadDefinitionResource}
 *
 * @since 3.10
 */
@Api(value = "Formats")
public interface UploadDefinitionResourceDoc
{
  @ApiOperation("Get upload field requirements for each supported format")
  List<UploadDefinitionXO> get();

  @ApiOperation("Get upload field requirements for the desired format")
  UploadDefinitionXO get(@ApiParam(value = "The desired repository format") final String format);
}
