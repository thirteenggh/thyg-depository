package org.sonatype.nexus.rapture.internal.logging

import groovy.transform.ToString

/**
 * LogEvent exchange-object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class LogEventXO
{
  Long timestamp

  String logger

  String level

  String message
}
