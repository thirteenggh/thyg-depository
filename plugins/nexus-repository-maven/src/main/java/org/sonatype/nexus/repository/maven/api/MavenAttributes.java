package org.sonatype.nexus.repository.maven.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * REST API model for describing maven specific repository properties.
 *
 * @since 3.20
 */
public class MavenAttributes
{
  @ApiModelProperty(value = "What type of artifacts does this repository store?",
      allowableValues = "RELEASE,SNAPSHOT,MIXED",
      example = "MIXED")
  @NotEmpty
  protected final String versionPolicy;

  @ApiModelProperty(value = "Validate that all paths are maven artifact or metadata paths",
      allowableValues = "STRICT,PERMISSIVE",
      example = "STRICT")
  @NotEmpty
  protected final String layoutPolicy;

  @JsonCreator
  public MavenAttributes(
      @JsonProperty("versionPolicy") final String versionPolicy,
      @JsonProperty("layoutPolicy") final String layoutPolicy)
  {
    this.versionPolicy = versionPolicy;
    this.layoutPolicy = layoutPolicy;
  }

  public String getVersionPolicy() {
    return versionPolicy;
  }

  public String getLayoutPolicy() {
    return layoutPolicy;
  }
}
