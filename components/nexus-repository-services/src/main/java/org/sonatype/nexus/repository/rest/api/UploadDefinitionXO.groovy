package org.sonatype.nexus.repository.rest.api

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.sonatype.nexus.repository.upload.UploadDefinition

/**
 * Upload definition transfer object for rest api
 *
 * @since 3.10
 */
@CompileStatic
@Builder
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['format'])
class UploadDefinitionXO
{
  String format

  boolean multipleUpload

  List<UploadFieldDefinitionXO> componentFields

  List<UploadFieldDefinitionXO> assetFields

  static UploadDefinitionXO from(final UploadDefinition uploadDefinition) {
    return  builder()
        .format(uploadDefinition.format)
        .multipleUpload(uploadDefinition.multipleUpload)
        .componentFields(uploadDefinition.componentFields.collect { UploadFieldDefinitionXO.from(it) })
        .assetFields(uploadDefinition.assetFields.collect { UploadFieldDefinitionXO.from(it) })
        .build()
  }
}
