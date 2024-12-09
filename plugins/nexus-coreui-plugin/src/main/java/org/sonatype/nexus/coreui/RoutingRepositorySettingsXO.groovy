package org.sonatype.nexus.coreui

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * Routing Repository Settings exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class RoutingRepositorySettingsXO
{
  @NotEmpty
  String repositoryId

  String publishStatus
  String publishMessage
  Date publishTimestamp
  String publishUrl

  Boolean discoveryEnabled = false
  Integer discoveryInterval
  String discoveryStatus
  String discoveryMessage
  Date discoveryTimestamp

}
