package org.sonatype.nexus.repository.rest.api

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.DESCRIPTION_DESCRIPTION
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.EXPRESSION_DESCRIPTION
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.EXPRESSION_EXAMPLE
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.EXPRESSION_NOTES
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.NAME_DESCRIPTION
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.TYPE_ALLOWED_VALUES
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.TYPE_DESCRIPTION
import static org.sonatype.nexus.repository.rest.internal.resources.doc.ContentSelectorsResourceDoc.TYPE_NOTES

/**
 * ContentSelector transfer object for REST APIs.
 *
 * @since 3.19
 */
@CompileStatic
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
@ApiModel
class ContentSelectorApiResponse
    implements ValidatableContentSelectorRequest
{
  @ApiModelProperty(value = NAME_DESCRIPTION)
  String name

  @ApiModelProperty(
      value = TYPE_DESCRIPTION,
      allowableValues = TYPE_ALLOWED_VALUES,
      notes = TYPE_NOTES
  )
  String type

  @ApiModelProperty(value = DESCRIPTION_DESCRIPTION)
  String description

  @ApiModelProperty(
      value = EXPRESSION_DESCRIPTION,
      example = EXPRESSION_EXAMPLE,
      notes = EXPRESSION_NOTES
  )
  String expression
}
