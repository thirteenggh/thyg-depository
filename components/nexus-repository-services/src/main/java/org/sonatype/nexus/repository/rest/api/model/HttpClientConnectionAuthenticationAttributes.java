package org.sonatype.nexus.repository.rest.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * REST API model for describing authentication for HTTP connections used by a proxy repository.
 *
 * @since 3.20
 */
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class HttpClientConnectionAuthenticationAttributes
{
  @ApiModelProperty(value = "Authentication type", allowableValues = "username,ntlm")
  @NotEmpty
  protected final String type;

  @ApiModelProperty
  protected final String username;

  @ApiModelProperty
  protected final String password;

  @ApiModelProperty
  protected final String ntlmHost;

  @ApiModelProperty
  protected final String ntlmDomain;

  @JsonCreator
  public HttpClientConnectionAuthenticationAttributes(
      @JsonProperty("type") final String type,
      @JsonProperty("username") final String username,
      @JsonProperty("password") final String password,
      @JsonProperty("ntlmHost") final String ntlmHost,
      @JsonProperty("ntlmDomain") final String ntlmDomain)
  {
    this.type = type;
    this.username = username;
    this.password = password;
    this.ntlmHost = ntlmHost;
    this.ntlmDomain = ntlmDomain;
  }

  public String getType() {
    return type;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getNtlmHost() {
    return ntlmHost;
  }

  public String getNtlmDomain() {
    return ntlmDomain;
  }
}
