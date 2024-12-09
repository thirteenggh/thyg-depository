package org.sonatype.nexus.coreui

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * BlobStore Quota Type exchange object.
 *
 * @since 3.14
 */
@ToString(includePackage = false, includeNames = true)
class BlobStoreQuotaTypeXO
{
  @NotBlank
  String id

  @NotBlank
  String name
}
