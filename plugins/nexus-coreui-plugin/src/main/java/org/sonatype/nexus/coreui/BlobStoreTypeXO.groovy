package org.sonatype.nexus.coreui

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * BlobStore Type exchange object.
 *
 * @since 3.6
 */
@ToString(includePackage = false, includeNames = true)
class BlobStoreTypeXO
{
  @NotBlank
  String id

  @NotBlank
  String name

  List<FormFieldXO> formFields

  String customFormName

  boolean isModifiable

  boolean isEnabled
}
