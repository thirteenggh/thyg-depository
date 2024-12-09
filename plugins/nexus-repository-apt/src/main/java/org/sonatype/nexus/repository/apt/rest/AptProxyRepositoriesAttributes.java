package org.sonatype.nexus.repository.apt.rest;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @since 3.20
 */
public class AptProxyRepositoriesAttributes
{
  @ApiModelProperty(value = "Distribution to fetch", example = "bionic")
  @NotEmpty
  private final String distribution;

  @ApiModelProperty(value = "Whether this repository is flat", example = "false")
  @NotNull
  private final Boolean flat;

  @JsonCreator
  public AptProxyRepositoriesAttributes(
      @JsonProperty("distribution") final String distribution,
      @JsonProperty("flat") final Boolean flat)
  {
    this.distribution = distribution;
    this.flat = flat;
  }

  public String getDistribution() {
    return distribution;
  }

  public Boolean getFlat() {
    return flat;
  }
}
