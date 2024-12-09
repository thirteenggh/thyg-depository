package org.sonatype.nexus.repository.rest.api.model;

import java.util.Collection;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * REST API model used to describe a group repository.
 *
 * @since 3.20
 */
public class GroupAttributes
{
  @ApiModelProperty(value = "Member repositories' names", dataType = "[Ljava.lang.String;")
  @NotEmpty
  protected final Collection<String> memberNames;

  @JsonCreator
  public GroupAttributes(@JsonProperty("memberNames") final Collection<String> memberNames) {
    this.memberNames = memberNames;
  }

  public Collection<String> getMemberNames() {
    return memberNames;
  }
}
