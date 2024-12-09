package org.sonatype.nexus.coreui

import groovy.transform.ToString

/**
 * Realm Security Settings exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class RealmSettingsXO
{
  List<String> realms
}
