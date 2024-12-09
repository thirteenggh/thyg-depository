package org.sonatype.nexus.coreui

import org.sonatype.nexus.security.realm.RealmExists
import org.sonatype.nexus.security.role.RolesExist
import org.sonatype.nexus.security.user.UserExists
import org.sonatype.nexus.validation.group.Create

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * User role mappings exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class UserRoleMappingsXO
{
  @NotBlank
  @UserExists(groups = Create)
  String userId

  @NotBlank
  @RealmExists(groups = Create)
  String realm

  @RolesExist
  Set<String> roles
}
