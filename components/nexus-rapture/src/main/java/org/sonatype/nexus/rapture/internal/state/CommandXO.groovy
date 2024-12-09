package org.sonatype.nexus.rapture.internal.state

import groovy.transform.ToString

/**
 * Command exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class CommandXO
{
  String type

  Object data
}
