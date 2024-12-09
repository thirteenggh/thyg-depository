package org.sonatype.nexus.blobstore.s3.rest.internal.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Encapsulates the S3 bucket details for an s3 blob store.
 *
 * @since 3.20
 */
@JsonInclude(NON_NULL)
public class S3BlobStoreApiBucket
{
  @Valid
  @NotNull
  @ApiModelProperty(value = "The AWS region to create a new S3 bucket in or an existing S3 bucket's region",
      required = true)
  private final String region;

  @Valid
  @NotNull
  @ApiModelProperty(value = "The name of the S3 bucket", required = true)
  private final String name;

  @ApiModelProperty("The S3 blob store (i.e S3 object) key prefix")
  private final String prefix;

  @Valid
  @NotNull
  @ApiModelProperty("How many days until deleted blobs are finally removed from the S3 bucket (-1 to disable)")
  private final Integer expiration;

  @JsonCreator
  public S3BlobStoreApiBucket(
      @JsonProperty("region") final String region,
      @JsonProperty("name") final String name,
      @JsonProperty("prefix") final String prefix,
      @JsonProperty("expiration") final Integer expiration)
  {
    this.region = region;
    this.name = name;
    this.prefix = prefix;
    this.expiration = expiration;
  }

  public String getRegion() {
    return region;
  }

  public String getName() {
    return name;
  }

  public String getPrefix() {
    return prefix;
  }

  public Integer getExpiration() {
    return expiration;
  }
}
