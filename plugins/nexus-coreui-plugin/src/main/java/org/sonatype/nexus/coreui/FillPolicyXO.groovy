package org.sonatype.nexus.coreui;

import groovy.transform.ToString

/**
 * Blob store group fill policy exchange object.
 *
 * @since 3.14
 */
@ToString(includePackage = false, includeNames = true)
class FillPolicyXO
{
  String id
  String name
}
