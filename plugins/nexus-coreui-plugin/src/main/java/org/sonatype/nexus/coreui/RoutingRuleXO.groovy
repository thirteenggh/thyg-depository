package org.sonatype.nexus.coreui

import javax.validation.constraints.Pattern

import org.sonatype.nexus.repository.routing.RoutingMode
import org.sonatype.nexus.validation.constraint.NamePatternConstants
import org.sonatype.nexus.validation.group.Create

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.hibernate.validator.constraints.NotBlank

/**
 * Routing Rule transfer object for internal REST API.
 *
 * @since 3.16
 */
@CompileStatic
@Builder
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['name'])
class RoutingRuleXO
{
  String id

  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @NotBlank(groups = Create)
  String name

  String description

  @NotBlank(groups = Create)
  RoutingMode mode

  @NotBlank
  List<String> matchers

  int assignedRepositoryCount

  List<String> assignedRepositoryNames
}
