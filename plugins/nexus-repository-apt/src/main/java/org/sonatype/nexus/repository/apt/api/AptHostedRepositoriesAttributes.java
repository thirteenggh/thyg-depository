package org.sonatype.nexus.repository.apt.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * REST API model of apt-specific hosted configuration.
 *
 * @since 3.20
 */
public class AptHostedRepositoriesAttributes
{
  @ApiModelProperty(value = "Distribution to fetch", example = "bionic")
  @NotEmpty
  protected final String distribution;

  @JsonCreator
  public AptHostedRepositoriesAttributes(@JsonProperty("distribution") final String distribution) {
    this.distribution = distribution;
  }

  public String getDistribution() {
    return distribution;
  }
}
