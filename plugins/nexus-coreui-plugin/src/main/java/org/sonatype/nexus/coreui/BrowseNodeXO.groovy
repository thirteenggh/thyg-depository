package org.sonatype.nexus.coreui

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * Component exchange object.
 *
 * @since 3.6
 */
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['id'])
class BrowseNodeXO {
  @NotBlank
  String id

  @NotBlank
  String text

  @NotBlank
  String type

  boolean leaf

  String componentId

  String assetId

  String packageUrl
}
