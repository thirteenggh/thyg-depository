package org.sonatype.nexus.rapture.internal.state

import groovy.transform.ToString

/**
 * Status exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class StatusXO
{
  String edition

  String version

  String buildRevision

  String buildTimestamp
}
