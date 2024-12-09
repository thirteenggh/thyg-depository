package org.sonatype.nexus.coreui

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty

/**
 * Asset exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ["id"])
class AssetXO {
  @NotEmpty
  String id

  @NotEmpty
  String name

  @NotEmpty
  String format

  @NotEmpty
  String contentType

  @NotEmpty
  long size

  @NotEmpty
  String repositoryName

  @NotEmpty
  String containingRepositoryName

  Date blobCreated

  Date blobUpdated

  Date lastDownloaded

  @NotEmpty
  String blobRef

  String componentId

  String createdBy

  String createdByIp

  @NotEmpty
  Map<String, Map<String, Object>> attributes
}
