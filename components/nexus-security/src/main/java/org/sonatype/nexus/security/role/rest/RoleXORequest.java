package org.sonatype.nexus.security.role.rest;

import java.util.Objects;
import java.util.Set;

import org.sonatype.nexus.security.internal.rest.NexusSecurityApiConstants;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class RoleXORequest
{
  @NotEmpty
  @ApiModelProperty(NexusSecurityApiConstants.ROLE_ID_DESCRIPTION)
  private String id;

  @NotEmpty
  @ApiModelProperty(NexusSecurityApiConstants.ROLE_NAME_DESCRIPTION)
  private String name;

  @ApiModelProperty(NexusSecurityApiConstants.ROLE_DESCRIPTION_DESCRIPTION)
  private String description;

  @ApiModelProperty(NexusSecurityApiConstants.ROLE_PRIVILEGES_DESCRIPTION)
  private Set<String> privileges;

  @ApiModelProperty(NexusSecurityApiConstants.ROLE_ROLES_DESCRIPTION)
  private Set<String> roles;

  public void setRoles(final Set<String> roles) {
    this.roles = roles;
  }

  public void setPrivileges(final Set<String> privileges) {
    this.privileges = privileges;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public Set<String> getPrivileges() {
    return privileges;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "id: " + id + " name: " + name + " description: " + description + " roles: " + roles
        + " privileges: " + privileges;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, roles, privileges);
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof RoleXORequest)) {
      return false;
    }

    RoleXORequest other = (RoleXORequest) obj;

    return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects
        .equals(description, other.description) && Objects.equals(roles, other.roles) && Objects
        .equals(privileges, other.privileges);
  }
}
