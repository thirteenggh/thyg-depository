package org.sonatype.nexus.httpclient.config;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.text.Strings2;

import org.hibernate.validator.constraints.NotBlank;

/**
 * NTLM authentication configuration.
 *
 * @since 3.0
 */
public class NtlmAuthenticationConfiguration
  extends AuthenticationConfiguration
{
  public static final String TYPE = "ntlm";

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @Nullable
  private String host;

  @Nullable
  private String domain;

  public NtlmAuthenticationConfiguration() {
    super(TYPE);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  @Nullable
  public String getHost() {
    return host;
  }

  public void setHost(@Nullable final String host) {
    this.host = host;
  }

  @Nullable
  public String getDomain() {
    return domain;
  }

  public void setDomain(@Nullable final String domain) {
    this.domain = domain;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "username='" + username + '\'' +
        ", password='" + Strings2.mask(password) + '\'' +
        ", host='" + host + '\'' +
        ", domain='" + domain + '\'' +
        '}';
  }
}
