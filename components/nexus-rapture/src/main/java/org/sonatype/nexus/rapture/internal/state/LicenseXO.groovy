package org.sonatype.nexus.rapture.internal.state

import groovy.transform.ToString

/**
 * License exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class LicenseXO
{
  boolean required

  boolean installed

  boolean valid

  int daysToExpiry

  List<String> features
}
