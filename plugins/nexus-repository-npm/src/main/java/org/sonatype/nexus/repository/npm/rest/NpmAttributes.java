package org.sonatype.nexus.repository.npm.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * @since 3.29
 */
public class NpmAttributes
{
  public static final String REMOVE_NON_CATALOGED = "removeNonCataloged";

  @ApiModelProperty(value = "Remove Non-Cataloged Versions", example = "true", required = true)
  private final Boolean removeNonCataloged;

  @JsonCreator
  public NpmAttributes(@JsonProperty(REMOVE_NON_CATALOGED) final Boolean removeNonCataloged) {
    this.removeNonCataloged = removeNonCataloged;
  }

  public Boolean getRemoveNonCataloged() {
    return removeNonCataloged;
  }
}
