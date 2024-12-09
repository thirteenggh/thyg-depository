package org.sonatype.nexus.internal.email.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static org.sonatype.nexus.repository.http.HttpStatus.BAD_REQUEST;
import static org.sonatype.nexus.repository.http.HttpStatus.FORBIDDEN;
import static org.sonatype.nexus.repository.http.HttpStatus.NO_CONTENT;
import static org.sonatype.nexus.repository.http.HttpStatus.OK;

/**
 * Swagger documentation for {@link EmailConfigurationApiResource}
 *
 * @since 3.19
 */
@Api(value = "Email")
public interface EmailConfigurationApiResourceDoc
{
  @ApiOperation("Retrieve the current email configuration")
  @ApiResponses(value = {
      @ApiResponse(code = FORBIDDEN, message = "Insufficient permissions to retrieve the email configuration")
  })
  ApiEmailConfiguration getEmailConfiguration();

  @ApiOperation("Set the current email configuration")
  @ApiResponses(value = {
      @ApiResponse(code = NO_CONTENT, message = "Email configuration was successfully updated"),
      @ApiResponse(code = BAD_REQUEST, message = "Invalid request"),
      @ApiResponse(code = FORBIDDEN, message = "Insufficient permissions to update the email configuration")
  })
  void setEmailConfiguration(@ApiParam(required = true) @NotNull @Valid ApiEmailConfiguration emailConfiguration);

  @ApiOperation("Send a test email to the email address provided in the request body")
  @ApiResponses(value = {
      @ApiResponse(code = OK, message = "Validation was complete, look at the body to determine success", response = ApiEmailValidation.class),
      @ApiResponse(code = FORBIDDEN, message = "Insufficient permissions to verify the email configuration")
  })
  ApiEmailValidation testEmailConfiguration(
      @ApiParam(required = true, value = "An email address to send a test email to") @NotNull String validationEmail
  );

  @ApiOperation("Disable and clear the email configuration")
  @ApiResponses(value = {
      @ApiResponse(code = NO_CONTENT, message = "Email configuration was successfully cleared")
  })
  void deleteEmailConfiguration();
}
