package org.sonatype.nexus.coreui.internal.capability

import org.sonatype.nexus.validation.group.Update

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * Capability notes exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class CapabilityNotesXO
{
  @NotEmpty(groups = Update.class)
  String id

  String notes
}
