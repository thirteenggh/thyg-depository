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
public abstract class ApiPrivilege
{
  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_TYPE_DESCRIPTION)
  private String type;

  @NotBlank
  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_NAME_DESCRIPTION)
  private String name;

  //note that the default value is "" as we want to match current UI behavior
  private String description = "";

  @ApiModelProperty(NexusSecurityApiConstants.PRIVILEGE_READONLY_DESCRIPTION)
  private boolean readOnly;

  public ApiPrivilege(final String type) {
    this(type, null, "", false);
  }

  public ApiPrivilege(final String type, final String name, final String description, final boolean readOnly) {
    this.type = type;
    this.name = name;
    this.description = description;
    this.readOnly = readOnly;
  }

  public ApiPrivilege(final Privilege privilege) {
    type = privilege.getType();
    name = privilege.getName();
    description = privilege.getDescription();
    readOnly = privilege.isReadOnly();
  }

  public void setType(final String type) {
    this.type = type;
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

  public void setReadOnly(final boolean readOnly) {
    this.readOnly = readOnly;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public Privilege asPrivilege() {
    Privilege privilege = new Privilege();
    privilege.setId(name);
    privilege.setName(name);
    privilege.setDescription(description);
    privilege.setReadOnly(readOnly);
    privilege.setType(type);

    return doAsPrivilege(privilege);
  }

  protected abstract Privilege doAsPrivilege(Privilege privilege);
}
