package org.sonatype.nexus.coreui

import groovy.transform.ToString

/**
 * Anonymous Security Settings exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class AnonymousSettingsXO
{
  Boolean enabled
  String userId
  String realmName
}
