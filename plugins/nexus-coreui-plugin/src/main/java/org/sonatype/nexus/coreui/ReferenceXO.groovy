package org.sonatype.nexus.coreui

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * Reference exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class ReferenceXO
{
  @NotBlank
  String id

  @NotBlank
  String name
}
