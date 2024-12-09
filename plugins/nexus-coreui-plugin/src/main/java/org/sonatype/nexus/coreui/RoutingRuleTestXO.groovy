package org.sonatype.nexus.coreui

import org.sonatype.nexus.repository.routing.RoutingMode

import groovy.transform.CompileStatic
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.hibernate.validator.constraints.NotBlank

/**
 * Routing Rule Test transfer object for internal REST API.
 *
 * @since 3.16
 */
@CompileStatic
@Builder
@ToString(includePackage = false, includeNames = true)
class RoutingRuleTestXO
{
  @NotBlank
  RoutingMode mode

  @NotBlank
  List<String> matchers

  @NotBlank
  String path
}
