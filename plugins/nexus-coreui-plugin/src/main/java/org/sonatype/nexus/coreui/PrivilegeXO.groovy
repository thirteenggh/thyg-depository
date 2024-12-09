package org.sonatype.nexus.coreui

import javax.validation.constraints.Pattern

import org.sonatype.nexus.security.privilege.UniquePrivilegeId
import org.sonatype.nexus.security.privilege.UniquePrivilegeName
import org.sonatype.nexus.validation.constraint.NamePatternConstants
import org.sonatype.nexus.validation.group.Create
import org.sonatype.nexus.validation.group.Update

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.NotEmpty

/**
 * Privilege exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class PrivilegeXO
{
  @NotBlank(groups = Update)
  @UniquePrivilegeId(groups = Create)
  String id

  @NotBlank(groups = Update)
  String version

  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @NotBlank
  @UniquePrivilegeName(groups = Create)
  String name

  String description

  @NotBlank
  String type

  Boolean readOnly

  @NotEmpty
  Map<String, String> properties

  String permission
}
