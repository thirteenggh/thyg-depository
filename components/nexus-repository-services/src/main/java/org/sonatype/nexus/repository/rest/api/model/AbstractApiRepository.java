package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.sonatype.nexus.validation.constraint.NamePatternConstants;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * REST API model of properties common to all repository types & formats.
 *
 * @since 3.20
 */
public abstract class AbstractApiRepository
{
  @ApiModelProperty(value = "A unique identifier for this repository", example = "internal")
  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @NotEmpty
  protected String name;

  @ApiModelProperty(value = "Component format held in this repository", example = "npm")
  @NotEmpty
  protected String format;

  @ApiModelProperty(value = "Controls if deployments of and updates to artifacts are allowed",
      allowableValues = "hosted,proxy,group", example = "hosted")
  @NotEmpty
  protected String type;

  @ApiModelProperty(value = "URL to the repository")
  protected String url;

  @ApiModelProperty(value = "Whether this repository accepts incoming requests", example = "true")
  @NotNull
  protected Boolean online;

  public AbstractApiRepository(
      final String name,
      final String format,
      final String type,
      final String url,
      final Boolean online)
  {
    this.name = name;
    this.format = format;
    this.type = type;
    this.url = url;
    this.online = online;
  }

  public String getName() {
    return name;
  }

  public String getFormat() {
    return format;
  }

  public String getType() {
    return type;
  }

  public Boolean getOnline() {
    return online;
  }

  public String getUrl() {
    return url;
  }
}
