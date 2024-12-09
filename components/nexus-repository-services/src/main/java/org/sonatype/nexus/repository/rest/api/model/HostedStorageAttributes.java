package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * REST API model for describing storage of hosted repositories.
 *
 * @since 3.20
 */
public class HostedStorageAttributes
    extends StorageAttributes
{
  @ApiModelProperty(value = "Controls if deployments of and updates to assets are allowed",
      allowableValues = "allow,allow_once,deny",
      example = "allow_once")
  @NotNull
  protected final String writePolicy;

  @JsonCreator
  public HostedStorageAttributes(
      @JsonProperty("blobStoreName") final String blobStoreName,
      @JsonProperty("strictContentTypeValidation") final Boolean strictContentTypeValidation,
      @JsonProperty("writePolicy") final String writePolicy)
  {
    super(blobStoreName, strictContentTypeValidation);
    this.writePolicy = writePolicy;
  }

  public String getWritePolicy() {
    return writePolicy;
  }
}
