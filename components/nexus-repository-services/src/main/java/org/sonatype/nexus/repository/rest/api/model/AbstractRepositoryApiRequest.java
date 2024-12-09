package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.sonatype.nexus.validation.constraint.NamePatternConstants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @since 3.20
 */
public abstract class AbstractRepositoryApiRequest
    implements RepositoryApiRequest
{
  @ApiModelProperty(value = "A unique identifier for this repository", example = "internal", required = true)
  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @NotEmpty
  protected String name;

  @ApiModelProperty(value = "Component format used by this repository", hidden = true)
  @NotEmpty
  protected String format;

  @ApiModelProperty(value = "Controls if deployments of and updates to artifacts are allowed",
      allowableValues = "hosted,proxy,group", hidden = true)
  @NotEmpty
  protected String type;

  @ApiModelProperty(value = "Whether this repository accepts incoming requests", example = "true", required = true)
  @NotNull
  protected Boolean online;

  @JsonCreator
  public AbstractRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("format") final String format,
      @JsonProperty("type") final String type,
      @JsonProperty("online") final Boolean online)
  {
    this.name = name;
    this.format = format;
    this.type = type;
    this.online = online;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getFormat() {
    return format;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public Boolean getOnline() {
    return online;
  }
}
