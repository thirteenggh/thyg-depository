package org.sonatype.nexus.security.anonymous.rest;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/**
 * @since 3.24
 */
@JsonInclude
public class AnonymousAccessSettingsXO
{
  @ApiModelProperty("Whether or not Anonymous Access is enabled")
  private boolean enabled = false;

  @NotBlank
  @ApiModelProperty("The username of the anonymous account")
  private String userId = AnonymousConfiguration.DEFAULT_USER_ID;

  @NotBlank
  @ApiModelProperty("The name of the authentication realm for the anonymous account")
  private String realmName = AnonymousConfiguration.DEFAULT_REALM_NAME;

  public AnonymousAccessSettingsXO() {
  }

  public AnonymousAccessSettingsXO(AnonymousConfiguration anonymousConfiguration) {
    this.enabled = anonymousConfiguration.isEnabled();
    this.userId = anonymousConfiguration.getUserId();
    this.realmName = anonymousConfiguration.getRealmName();
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getRealmName() {
    return realmName;
  }

  public void setRealmName(final String realmName) {
    this.realmName = realmName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnonymousAccessSettingsXO that = (AnonymousAccessSettingsXO) o;
    return enabled == that.enabled &&
        Objects.equals(userId, that.userId) &&
        Objects.equals(realmName, that.realmName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enabled, userId, realmName);
  }
}
