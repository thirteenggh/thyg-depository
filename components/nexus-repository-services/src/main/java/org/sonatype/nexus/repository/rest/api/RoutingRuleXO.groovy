package org.sonatype.nexus.repository.rest.api

import javax.validation.constraints.Pattern

import org.sonatype.nexus.repository.routing.RoutingMode
import org.sonatype.nexus.repository.routing.RoutingRule
import org.sonatype.nexus.validation.constraint.NamePatternConstants

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder
import io.swagger.annotations.ApiModelProperty

/**
 * Routing Rule transfer object for public REST API.
 *
 * @since 3.16
 */
@CompileStatic
@Builder
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['name'])
class RoutingRuleXO
{
  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  String name

  @ApiModelProperty(allowEmptyValue = true)
  String description

  @ApiModelProperty(
      value = "Determines what should be done with requests when their path matches any of the matchers",
      allowableValues = "BLOCK,ALLOW"
  )
  RoutingMode mode

  @ApiModelProperty(
      value = "Regular expressions used to identify request paths that are allowed or blocked (depending on mode)"
  )
  List<String> matchers

  static RoutingRuleXO fromRoutingRule(final RoutingRule routingRule) {
    return builder()
      .name(routingRule.name())
      .description(routingRule.description())
      .mode(routingRule.mode())
      .matchers(routingRule.matchers())
      .build()
  }
}
