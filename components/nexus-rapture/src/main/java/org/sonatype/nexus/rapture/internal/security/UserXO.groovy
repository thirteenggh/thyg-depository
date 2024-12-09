package org.sonatype.nexus.rapture.internal.security

import groovy.transform.ToString

// TODO: Rename to CurrentUserXO?

/**
 * User exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class UserXO
{
  String id

  boolean authenticated

  /**
   * True if the current user has general administrator privileges (ie. admin role).
   */
  boolean administrator

  Set<String> authenticatedRealms
}
