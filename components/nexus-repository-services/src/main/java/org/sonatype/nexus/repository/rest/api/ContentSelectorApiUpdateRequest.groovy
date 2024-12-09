package org.sonatype.nexus.repository.rest.api


import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.NotBlank

import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.*

/**
 * ContentSelector transfer object for REST APIs.
 *
 * @since 3.19
 */
@CompileStatic
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
@ApiModel
class ContentSelectorApiUpdateRequest
    implements ValidatableContentSelectorRequest
{
  @ApiModelProperty(value = "An optional description of this content selector", allowEmptyValue = true)
  String description

  @ApiModelProperty(
      value = EXPRESSION_DESCRIPTION,
      example = EXPRESSION_EXAMPLE,
      notes = EXPRESSION_NOTES
  )
  @NotBlank
  String expression
}
