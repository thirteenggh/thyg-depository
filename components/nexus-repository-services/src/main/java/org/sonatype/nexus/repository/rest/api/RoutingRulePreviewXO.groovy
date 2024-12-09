package org.sonatype.nexus.repository.rest.api

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.hibernate.validator.constraints.NotBlank

/**
 * Routing Rule Preview grid row transfer object.
 *
 * @since 3.17
 */
@CompileStatic
@Builder
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['text'])
class RoutingRulePreviewXO
{
  @NotBlank
  String repository

  String type

  String format

  String rule

  Boolean allowed

  Boolean expanded

  Boolean expandable

  List<RoutingRulePreviewXO> children
}
