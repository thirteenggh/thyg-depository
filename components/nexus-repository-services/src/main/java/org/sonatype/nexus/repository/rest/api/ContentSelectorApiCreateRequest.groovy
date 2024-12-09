package org.sonatype.nexus.repository.rest.api

import javax.validation.constraints.Pattern

import org.sonatype.nexus.validation.constraint.NamePatternConstants

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.NotBlank

import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.DESCRIPTION_DESCRIPTION
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.EXPRESSION_DESCRIPTION
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.EXPRESSION_EXAMPLE
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.EXPRESSION_NOTES
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.NAME_DESCRIPTION

/**
 * ContentSelector transfer object for REST APIs.
 *
 * @since 3.19
 */
@CompileStatic
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
@ApiModel
class ContentSelectorApiCreateRequest
    implements ValidatableContentSelectorRequest
{
  @ApiModelProperty(value = NAME_DESCRIPTION)
  @Pattern(regexp = NamePatternConstants.REGEX, message = NamePatternConstants.MESSAGE)
  @NotBlank
  String name

  @ApiModelProperty(value = DESCRIPTION_DESCRIPTION, allowEmptyValue = true)
  String description

  @ApiModelProperty(
      value = EXPRESSION_DESCRIPTION,
      example = EXPRESSION_EXAMPLE,
      notes = EXPRESSION_NOTES
  )
  @NotBlank
  String expression
}
