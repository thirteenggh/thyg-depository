package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * REST API model describing a repository's storage settings.
 *
 * @since 3.20
 */
public class StorageAttributes
{
  @ApiModelProperty(value = "Blob store used to store repository contents", example = "default")
  @NotEmpty
  protected String blobStoreName;

  @ApiModelProperty(value = "Whether to validate uploaded content's MIME type appropriate for the repository format",
      example = "true")
  @NotNull
  protected Boolean strictContentTypeValidation;

  @JsonCreator
  public StorageAttributes(
      @JsonProperty("blobStoreName") final String blobStoreName,
      @JsonProperty("strictContentTypeValidation") final Boolean strictContentTypeValidation)
  {
    this.blobStoreName = blobStoreName;
    this.strictContentTypeValidation = strictContentTypeValidation;
  }

  public String getBlobStoreName() {
    return blobStoreName;
  }

  public Boolean getStrictContentTypeValidation() {
    return strictContentTypeValidation;
  }
}
