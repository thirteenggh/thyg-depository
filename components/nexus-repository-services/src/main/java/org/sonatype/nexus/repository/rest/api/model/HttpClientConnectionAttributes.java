package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * REST API model specifying the HTTP connection used by a proxy repository.
 *
 * @since 3.20
 */
public class HttpClientConnectionAttributes
{
  @ApiModelProperty(value = "Total retries if the initial connection attempt suffers a timeout", example = "0",
      allowableValues = "range[0,10]")
  @Min(0L)
  @Max(10L)
  protected final Integer retries;

  @ApiModelProperty(value = "Custom fragment to append to User-Agent header in HTTP requests", example = "")
  protected final String userAgentSuffix;

  @ApiModelProperty(value = "Seconds to wait for activity before stopping and retrying the connection", example = "60",
      allowableValues = "range[1,3600]")
  @Min(1L)
  @Max(3600L)
  protected final Integer timeout;

  @ApiModelProperty(value = "Whether to enable redirects to the same location (may be required by some servers)",
      example = "false")
  protected final Boolean enableCircularRedirects;

  @ApiModelProperty(value = "Whether to allow cookies to be stored and used", example = "false")
  protected final Boolean enableCookies;

  @JsonCreator
  public HttpClientConnectionAttributes(
      @JsonProperty("retries") final Integer retries,
      @JsonProperty("userAgentSuffix") final String userAgentSuffix,
      @JsonProperty("timeout") final Integer timeout,
      @JsonProperty("enableCircularRedirects") final Boolean enableCircularRedirects,
      @JsonProperty("enableCookies") final Boolean enableCookies)
  {
    this.retries = retries;
    this.userAgentSuffix = userAgentSuffix;
    this.timeout = timeout;
    this.enableCircularRedirects = enableCircularRedirects;
    this.enableCookies = enableCookies;
  }

  public Integer getRetries() {
    return retries;
  }

  public String getUserAgentSuffix() {
    return userAgentSuffix;
  }

  public Integer getTimeout() {
    return timeout;
  }

  public Boolean getEnableCircularRedirects() {
    return enableCircularRedirects;
  }

  public Boolean getEnableCookies() {
    return enableCookies;
  }
}
