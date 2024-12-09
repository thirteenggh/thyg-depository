package org.sonatype.nexus.blobstore.s3.rest.internal.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.sonatype.nexus.blobstore.rest.BlobStoreApiSoftQuota;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Encapsulates the API payload for creating, reading and updating an S3 blob store.
 *
 * @since 3.20
 */
public class S3BlobStoreApiModel
{
  @NotNull
  @ApiModelProperty(value = "The name of the S3 blob store.", required = true)
  private String name;

  @ApiModelProperty("Settings to control the soft quota.")
  private final BlobStoreApiSoftQuota softQuota;

  @Valid
  @NotNull
  @ApiModelProperty("The S3 specific configuration details for the S3 object that'll contain the blob store.")
  private final S3BlobStoreApiBucketConfiguration bucketConfiguration;

  @JsonCreator
  public S3BlobStoreApiModel(
      @JsonProperty("name") final String name,
      @JsonProperty("softQuota") final BlobStoreApiSoftQuota softQuota,
      @JsonProperty("bucketConfiguration")
      final S3BlobStoreApiBucketConfiguration bucketConfiguration)
  {
    this.name = name;
    this.softQuota = softQuota;
    this.bucketConfiguration = bucketConfiguration;
  }

  public String getName() {
    return name;
  }

  public BlobStoreApiSoftQuota getSoftQuota() {
    return softQuota;
  }

  public S3BlobStoreApiBucketConfiguration getBucketConfiguration() {
    return bucketConfiguration;
  }
}
