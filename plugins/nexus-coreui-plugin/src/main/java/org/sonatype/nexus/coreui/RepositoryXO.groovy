package org.sonatype.nexus.coreui

import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

import org.sonatype.nexus.validation.constraint.NamePatternConstants
import org.sonatype.nexus.repository.config.UniqueRepositoryName
import org.sonatype.nexus.validation.group.Create

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.NotEmpty

/**
 * Repository exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class RepositoryXO
{
  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @NotEmpty
  @UniqueRepositoryName(groups = Create)
  String name

  String type

  String format

  @NotBlank(groups = Create)
  String recipe

  @NotNull
  Boolean online

  String routingRuleId

  @NotEmpty
  Map<String, Map<String, Object>> attributes

  String url

  RepositoryStatusXO status
}
