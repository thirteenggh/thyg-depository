package org.sonatype.nexus.coreui

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * Privilege Type exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class PrivilegeTypeXO
{
  @NotEmpty
  String id

  @NotEmpty
  String name

  List<FormFieldXO> formFields
}
