package org.sonatype.nexus.coreui

import groovy.transform.ToString


/**
 * Repository reference exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class RepositoryReferenceXO
    extends ReferenceXO
{
  String type
  String format
  String versionPolicy
  String url
  RepositoryStatusXO status
  /**
   * sortOrder will override the typical alphanumeric ordering in the UI, so the higher your sortOrder, the closer to
   * the top you will get
   */
  int sortOrder = 0
}
