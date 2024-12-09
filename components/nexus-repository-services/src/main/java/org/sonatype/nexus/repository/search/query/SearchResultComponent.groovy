package org.sonatype.nexus.repository.search.query

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * Search Result Component object.
 *
 * @since 3.14
 */
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['id'])
class SearchResultComponent
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
