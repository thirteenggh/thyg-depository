package org.sonatype.nexus.repository.rest.api

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.sonatype.nexus.repository.upload.UploadFieldDefinition

/**
 * Upload field definition transfer object for rest api
 *
 * @since 3.10
 */
@CompileStatic
@Builder
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['name'])
class UploadFieldDefinitionXO
{
  String name

  String type

  String description

  boolean optional

  String group

  static UploadFieldDefinitionXO from(final UploadFieldDefinition uploadFieldDefinition) {
    return builder()
        .name(uploadFieldDefinition.name)
        .type(uploadFieldDefinition.type.name())
        .description(uploadFieldDefinition.helpText)
        .optional(uploadFieldDefinition.optional)
        .group(uploadFieldDefinition.group)
        .build()
  }
}
