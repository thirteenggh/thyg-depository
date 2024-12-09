package org.sonatype.nexus.coreui

import javax.validation.constraints.NotEmpty

/**
 * @since 3.24
 */
class UserAccountPasswordXO {
  @NotEmpty
  String authToken

  @NotEmpty
  String password
}
