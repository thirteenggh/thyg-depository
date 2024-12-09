package org.sonatype.nexus.coreui

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * Component exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['id'])
class ComponentXO
{
  @NotBlank
  String id

  @NotBlank
  String repositoryName

  @NotBlank
  String group

  @NotBlank
  String name

  @NotBlank
  String version

  @NotBlank
  String format
}
