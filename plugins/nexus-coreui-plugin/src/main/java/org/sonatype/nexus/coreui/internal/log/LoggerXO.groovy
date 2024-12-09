package org.sonatype.nexus.coreui.internal.log

import javax.validation.constraints.NotNull

import org.sonatype.nexus.common.log.LoggerLevel

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * Logger exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class LoggerXO
{
  @NotEmpty
  String name

  @NotNull
  LoggerLevel level

  boolean override

  static LoggerXO fromEntry(Map.Entry<String, LoggerLevel> entry) {
    return new LoggerXO(name: entry.getKey(), level: entry.getValue());
  }
}
