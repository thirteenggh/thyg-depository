package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * REST API model for describing HTTP connection properties for proxy repositories.
 *
 * @since 3.20
 */
public class HttpClientAttributes
{
  @ApiModelProperty(value = "Whether to block outbound connections on the repository", example = "false")
  @NotNull
  protected final Boolean blocked;

  @ApiModelProperty(
      value = "Whether to auto-block outbound connections if remote peer is detected as unreachable/unresponsive",
      example = "true")
  @NotNull
  protected final Boolean autoBlock;

  @Valid
  protected final HttpClientConnectionAttributes connection;

  @Valid
  protected final HttpClientConnectionAuthenticationAttributes authentication;

  @JsonCreator
  public HttpClientAttributes(
      @JsonProperty("blocked") final Boolean blocked,
      @JsonProperty("autoBlock") final Boolean autoBlock,
      @JsonProperty("connection") final HttpClientConnectionAttributes connection,
      @JsonProperty("authentication") final HttpClientConnectionAuthenticationAttributes authentication)
  {
    this.blocked = blocked;
    this.autoBlock = autoBlock;
    this.connection = connection;
    this.authentication = authentication;
  }

  public Boolean getBlocked() {
    return blocked;
  }

  public Boolean getAutoBlock() {
    return autoBlock;
  }

  public HttpClientConnectionAttributes getConnection() {
    return connection;
  }

  public HttpClientConnectionAuthenticationAttributes getAuthentication() {
    return authentication;
  }
}
