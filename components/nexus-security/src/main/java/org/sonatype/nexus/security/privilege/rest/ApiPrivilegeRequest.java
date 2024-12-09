package org.sonatype.nexus.security.privilege.rest;

import javax.validation.constraints.Pattern;

import org.sonatype.nexus.security.internal.rest.NexusSecurityApiConstants;
import org.sonatype.nexus.security.privilege.Privilege;
import org.sonatype.nexus.validation.constraint.NamePatternConstants;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @since 3.19
 */
public abstract class ApiPrivilegeRequest
{
  @NotBlank
  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_NAME_DESCRIPTION)
  private String name;

  //note that the default value is "" as we want to match current UI behavior
  private String description = "";

  public ApiPrivilegeRequest() {
    this(null, "");
  }

  public ApiPrivilegeRequest(final String name, final String description) {
    this.name = name;
    this.description = description;
  }

  public ApiPrivilegeRequest(final Privilege privilege) {
    name = privilege.getName();
    description = privilege.getDescription();
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setDescription(final String description) {
    if (description == null) {
      this.description = "";
    }
    else {
      this.description = description;
    }
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Privilege asPrivilege() {
    Privilege privilege = new Privilege();
    privilege.setId(name);
    privilege.setName(name);
    privilege.setDescription(description);

    return doAsPrivilege(privilege);
  }

  protected abstract Privilege doAsPrivilege(Privilege privilege);
}
