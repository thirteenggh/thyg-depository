package org.sonatype.nexus.httpclient.config;

import org.sonatype.nexus.common.text.Strings2;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Username(+password) authentication configuration.
 *
 * @since 3.0
 */
public class UsernameAuthenticationConfiguration
  extends AuthenticationConfiguration
{
  public static final String TYPE = "username";

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  public UsernameAuthenticationConfiguration() {
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

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "username='" + username + '\'' +
        ", password='" + Strings2.mask(password) + '\'' +
        '}';
  }
}
