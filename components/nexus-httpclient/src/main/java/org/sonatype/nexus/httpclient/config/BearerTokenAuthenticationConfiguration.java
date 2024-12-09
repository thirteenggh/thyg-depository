package org.sonatype.nexus.httpclient.config;

import org.sonatype.nexus.common.text.Strings2;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Bearer Token authentication configuration
 *
 * @since 3.20
 */
public class BearerTokenAuthenticationConfiguration
  extends AuthenticationConfiguration
{
  public static final String TYPE = "bearerToken";

  @NotBlank
  private String bearerToken;

  public BearerTokenAuthenticationConfiguration() {
    super(TYPE);
  }

  public String getBearerToken() {
    return bearerToken;
  }

  public void setBearerToken(final String bearerToken) {
    this.bearerToken = bearerToken;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "bearerToken='" + Strings2.mask(bearerToken) + '\'' +
        '}';
  }
}
