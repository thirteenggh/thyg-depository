package org.sonatype.nexus.blobstore.s3.rest.internal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Encapsulates S3 endpoint url, signer type and whether path-style access should be enabled for the specified S3
 * endpoint url.
 *
 * @since 3.20
 */
@JsonInclude(NON_NULL)
public class S3BlobStoreApiAdvancedBucketConnection
{
  @ApiModelProperty("A custom endpoint URL for third party object stores using the S3 API.")
  private final String endpoint;

  @ApiModelProperty("An API signature version which may be required for third party object stores using the S3 API.")
  private final String signerType;

  @ApiModelProperty("Setting this flag will result in path-style access being used for all requests.")
  private final Boolean forcePathStyle;

  @JsonCreator
  public S3BlobStoreApiAdvancedBucketConnection(
      @JsonProperty("endpoint") final String endpoint,
      @JsonProperty("signerType") final String signerType,
      @JsonProperty("forcePathStyle") final Boolean forcePathStyle)
  {
    this.endpoint = endpoint;
    this.signerType = signerType;
    this.forcePathStyle = forcePathStyle;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public String getSignerType() {
    return signerType;
  }

  public Boolean getForcePathStyle() {
    return forcePathStyle;
  }
}
