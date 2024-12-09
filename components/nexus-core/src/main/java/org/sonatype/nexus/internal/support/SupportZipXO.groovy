package org.sonatype.nexus.internal.support

import groovy.transform.ToString

/**
 * Support Zip exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class SupportZipXO
{
  String file
  String name
  String size
  Boolean truncated
}
