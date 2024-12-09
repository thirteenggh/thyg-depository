package org.sonatype.nexus.coreui

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * User account exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class UserAccountXO
{
  @NotEmpty
  String userId

  @NotEmpty
  String firstName

  @NotEmpty
  String lastName

  @NotEmpty
  String email

  Boolean external
}
