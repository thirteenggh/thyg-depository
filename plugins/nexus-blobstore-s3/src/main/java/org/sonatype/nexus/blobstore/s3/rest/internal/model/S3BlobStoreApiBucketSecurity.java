package org.sonatype.nexus.blobstore.s3.rest.internal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Encapsulates the IAM settings to use for accessing an s3 blob store.
 *
 * @since 3.20
 */
@JsonInclude(NON_NULL)
public class S3BlobStoreApiBucketSecurity
{
  @ApiModelProperty("An IAM access key ID for granting access to the S3 bucket")
  private final String accessKeyId;

  @ApiModelProperty("The secret access key associated with the specified IAM access key ID")
  private final String secretAccessKey;

  @ApiModelProperty("An IAM role to assume in order to access the S3 bucket")
  private final String role;

  @ApiModelProperty(
      "An AWS STS session token associated with temporary security credentials which grant access to the S3 bucket")
  private final String sessionToken;

  @JsonCreator
  public S3BlobStoreApiBucketSecurity(
      @JsonProperty("accessKeyId") final String accessKeyId,
      @JsonProperty("secretAccessKey") final String secretAccessKey,
      @JsonProperty("role") final String role,
      @JsonProperty("sessionToken") final String sessionToken)
  {
    this.accessKeyId = accessKeyId;
    this.secretAccessKey = secretAccessKey;
    this.role = role;
    this.sessionToken = sessionToken;
  }

  public String getAccessKeyId() {
    return accessKeyId;
  }

  public String getSecretAccessKey() {
    return secretAccessKey;
  }

  public String getRole() {
    return role;
  }

  public String getSessionToken() {
    return sessionToken;
  }
}
