package org.sonatype.nexus.coreui

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * Privilege exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class PrivilegeRepositoryTargetXO
{
  @NotEmpty
  String name

  @NotEmpty
  String description

  @NotEmpty
  String repositoryTargetId

  /**
   * Empty value is interpreted as 'All Repositories'
   */
  String repositoryId
}
