package org.sonatype.nexus.repository.rest.api.model;

import java.util.Collection;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * @since 3.27
 */
public class GroupDeployAttributes
    extends GroupAttributes
{
  @ApiModelProperty(value = "Pro-only: This field is for the Group Deployment feature available in NXRM Pro.")
  @Nullable
  @JsonInclude(Include.NON_NULL)
  protected final String writableMember;

  @JsonCreator
  public GroupDeployAttributes(
      @JsonProperty("memberNames") final Collection<String> memberNames,
      @JsonProperty("writableMember") @Nullable final String writableMember)
  {
    super(memberNames);
    this.writableMember = writableMember;
  }

  @Nullable
  public String getWritableMember() {
    return writableMember;
  }
}
