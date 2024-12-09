package org.sonatype.nexus.coreui.internal.capability

import javax.validation.constraints.NotNull

import org.sonatype.nexus.capability.CapabilityTypeExists
import org.sonatype.nexus.validation.group.Create
import org.sonatype.nexus.validation.group.Update

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.NotEmpty

/**
 * Capability exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class CapabilityXO
{
  @NotEmpty(groups = Update)
  String id

  @NotBlank(groups = Create)
  @CapabilityTypeExists(groups = Create)
  String typeId

  @NotNull
  Boolean enabled

  String notes

  Map<String, String> properties

  Boolean active

  Boolean error

  String description

  String state

  String stateDescription

  String status

  String typeName

  Map<String, String> tags
}
