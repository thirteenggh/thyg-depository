package org.sonatype.nexus.internal.wonderland;

import java.util.Objects;

/**
 * @since 3.15
 */
public class UserAuthToken
{
  private final String user;
  private final String token;

  public UserAuthToken(final String user, final String token) {
    this.user = user;
    this.token = token;
  }

  public String getUser() {
    return user;
  }

  public String getToken() {
    return token;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof UserAuthToken)) {
      return false;
    }

    UserAuthToken other = (UserAuthToken) obj;

    return Objects.equals(getUser(), other.getUser()) && Objects.equals(getToken(), other.getToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUser(), getToken());
  }

  @Override
  public String toString() {
    return getUser() + ":" + getToken();
  }
}
