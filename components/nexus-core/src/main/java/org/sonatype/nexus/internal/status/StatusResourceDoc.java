package org.sonatype.nexus.internal.status;

import java.util.SortedMap;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

import com.codahale.metrics.health.HealthCheck.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST API for status operations
 *
 * @since 3.15
 */
@Api("Status")
public interface StatusResourceDoc
{
  /**
   * @return 200 if the server is available to serve read requests, 503 otherwise
   */
  @GET
  @ApiOperation("Health check endpoint that validates server can respond to read requests")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Available to service requests"),
      @ApiResponse(code = 503, message = "Unavailable to service requests")
  })
  Response isAvailable();

  /**
   * @return 200 if the server is available to serve read and write requests, 503 otherwise
   *
   * @since 3.16
   */
  @GET
  @ApiOperation("Health check endpoint that validates server can respond to read and write requests")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Available to service requests"),
      @ApiResponse(code = 503, message = "Unavailable to service requests")
  })
  Response isWritable();

  /**
   * @since 3.20
   */
  @GET
  @ApiOperation("Health check endpoint that returns the results of the system status checks")
  @ApiResponses({
      @ApiResponse(code = 200, message = "The system status check results", response = Result.class, responseContainer = "Map")
  })
  SortedMap<String, Result> getSystemStatusChecks();

}
