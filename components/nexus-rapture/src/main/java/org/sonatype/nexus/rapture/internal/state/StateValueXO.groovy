package org.sonatype.nexus.rapture.internal.state

import groovy.transform.ToString

/**
 * State value exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class StateValueXO
{
  String hash

  Object value
}
