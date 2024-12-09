package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.validation.constraint.UriString;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * REST API model describing a proxy repository.
 *
 * @since 3.20
 */
public class ProxyAttributes
{
  @ApiModelProperty(value = "Location of the remote repository being proxied", example = "https://remote.repository.com")
  @UriString
  @NotEmpty
  protected final String remoteUrl;

  @ApiModelProperty(value = "How long to cache artifacts before rechecking the remote repository (in minutes)",
      example = "1440")
  @NotNull
  protected final Integer contentMaxAge;

  @ApiModelProperty(value = "How long to cache metadata before rechecking the remote repository (in minutes)",
      example = "1440")
  @NotNull
  protected final Integer metadataMaxAge;

  @JsonCreator
  public ProxyAttributes(
      @JsonProperty("remoteUrl") final String remoteUrl,
      @JsonProperty("contentMaxAge") final Integer contentMaxAge,
      @JsonProperty("metadataMaxAge") final Integer metadataMaxAge)
  {
    this.remoteUrl = remoteUrl;
    this.contentMaxAge = contentMaxAge;
    this.metadataMaxAge = metadataMaxAge;
  }

  public String getRemoteUrl() {
    return remoteUrl;
  }

  public Integer getContentMaxAge() {
    return contentMaxAge;
  }

  public Integer getMetadataMaxAge() {
    return metadataMaxAge;
  }
}
