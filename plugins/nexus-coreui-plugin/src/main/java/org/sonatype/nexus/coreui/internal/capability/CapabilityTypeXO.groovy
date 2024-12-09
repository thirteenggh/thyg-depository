package org.sonatype.nexus.coreui.internal.capability

import org.sonatype.nexus.coreui.FormFieldXO

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * Capability Type exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class CapabilityTypeXO
{
  @NotEmpty
  String id

  @NotEmpty
  String name
  
  String about
  
  List<FormFieldXO> formFields
}
