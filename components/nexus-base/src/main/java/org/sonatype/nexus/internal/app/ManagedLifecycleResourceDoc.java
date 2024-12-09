package org.sonatype.nexus.internal.app;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST API to manage the Nexus application lifecycle.
 *
 * @since 3.16
 */
@Api("Lifecycle")
public interface ManagedLifecycleResourceDoc
{
  @ApiOperation("Get current lifecycle phase")
  String getPhase();

  @ApiOperation("Move to new lifecycle phase")
  void setPhase(@ApiParam("The phase to move to") final String phase);

  @ApiOperation(value = "Bounce lifecycle phase", notes = "Re-runs all phases from the given phase to the current phase")
  void bounce(@ApiParam("The phase to bounce") final String phase);
}
