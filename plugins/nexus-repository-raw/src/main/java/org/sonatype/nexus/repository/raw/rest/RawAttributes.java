package org.sonatype.nexus.repository.raw.rest;

import org.sonatype.nexus.content.raw.internal.recipe.ContentDisposition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * REST API model of raw attributes for repositories API
 *
 * @since 3.25
 */
public class RawAttributes
{

  public static final String CONTENT_DISPOSITION = "contentDisposition";

  @ApiModelProperty(value = "Content Disposition",
      allowableValues = "INLINE,ATTACHMENT", example = "ATTACHMENT")
  private final ContentDisposition contentDisposition;

  @JsonCreator
  public RawAttributes(
      @JsonProperty(CONTENT_DISPOSITION) final ContentDisposition contentDisposition
  )
  {
    this.contentDisposition = contentDisposition;
  }

  public ContentDisposition getContentDisposition() {
    return contentDisposition;
  }
}
