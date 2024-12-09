package org.sonatype.nexus.coreui

import org.sonatype.nexus.security.privilege.PrivilegesExist
import org.sonatype.nexus.security.role.RoleNotContainSelf
import org.sonatype.nexus.security.role.RolesExist
import org.sonatype.nexus.security.role.UniqueRoleId
import org.sonatype.nexus.validation.group.Create
import org.sonatype.nexus.validation.group.Update

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * Role exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
@RoleNotContainSelf(id="getId", roleIds="getRoles")
class RoleXO
{
  @NotEmpty
  @UniqueRoleId(groups = Create)
  String id

  @NotEmpty(groups = Update)
  String version

  String source

  @NotEmpty
  String name

  String description

  Boolean readOnly

  @PrivilegesExist(groups = [Create, Update])
  Set<String> privileges

  @RolesExist(groups = [Create, Update])
  Set<String> roles
}
