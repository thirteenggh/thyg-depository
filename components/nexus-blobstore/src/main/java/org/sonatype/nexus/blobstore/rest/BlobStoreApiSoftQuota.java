package org.sonatype.nexus.blobstore.rest;

import org.sonatype.nexus.blobstore.quota.internal.SpaceRemainingQuota;
import org.sonatype.nexus.blobstore.quota.internal.SpaceUsedQuota;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * @since 3.19
 */
public class BlobStoreApiSoftQuota
{
  @NotBlank
  @ApiModelProperty("The type to use such as " + SpaceRemainingQuota.ID + ", or " + SpaceUsedQuota.ID)
  private String type;

  @Range
  @ApiModelProperty("The limit in MB.")
  private Long limit;

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public Long getLimit() {
    return limit;
  }

  public void setLimit(final Long limit) {
    this.limit = limit;
  }
}
