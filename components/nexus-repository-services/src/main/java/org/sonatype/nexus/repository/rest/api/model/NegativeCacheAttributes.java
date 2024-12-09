package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * REST API model of a proxy repository's negative cache settings.
 *
 * @since 3.20
 */
public class NegativeCacheAttributes
{
  @ApiModelProperty(value = "Whether to cache responses for content not present in the proxied repository",
      example = "true")
  @NotNull
  protected final Boolean enabled;

  @ApiModelProperty(value = "How long to cache the fact that a file was not found in the repository (in minutes)",
      example = "1440")
  @NotNull
  protected final Integer timeToLive;

  @JsonCreator
  public NegativeCacheAttributes(
      @JsonProperty("enabled") final Boolean enabled,
      @JsonProperty("timeToLive") final Integer timeToLive)
  {
    this.enabled = enabled;
    this.timeToLive = timeToLive;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Integer getTimeToLive() {
    return timeToLive;
  }
}
