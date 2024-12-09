package org.sonatype.nexus.coreui

import javax.validation.constraints.NotNull

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * Task type exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class TaskTypeXO
{
  @NotBlank
  String id

  @NotBlank
  String name

  @NotNull
  Boolean exposed

  @NotNull
  Boolean concurrentRun
  
  List<FormFieldXO> formFields
}
