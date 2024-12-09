package org.sonatype.nexus.repository.apt.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * REST API model for apt-specific proxy attributes.
 *
 * @since 3.20
 */
public class AptProxyRepositoriesAttributes
    extends AptHostedRepositoriesAttributes
{
  @ApiModelProperty(value = "Whether this repository is flat", example = "false")
  @NotEmpty
  protected final Boolean flat;

  @JsonCreator
  public AptProxyRepositoriesAttributes(
      @JsonProperty("distribution") final String distribution,
      @JsonProperty("flat") final Boolean flat)
  {
    super(distribution);
    this.flat = flat;
  }

  public Boolean getFlat() {
    return flat;
  }
}
