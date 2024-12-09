
package org.sonatype.nexus.security.internal.rest;

import org.sonatype.nexus.security.user.UserStatus;

/**
 * @since 3.17
 */
public enum ApiUserStatus
{
  active(UserStatus.active), locked(UserStatus.locked), disabled(UserStatus.disabled), changepassword(
      UserStatus.changepassword); // NOSONAR

  private UserStatus status;

  ApiUserStatus(final UserStatus status) {
    this.status = status;
  }

  UserStatus getStatus() {
    return status;
  }

  public static ApiUserStatus convert(final UserStatus status) {
    if (status == null) {
      return null;
    }
    return ApiUserStatus.valueOf(status.toString());
  }
}
