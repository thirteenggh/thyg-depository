
package org.sonatype.nexus.security.internal.rest;

import org.sonatype.nexus.security.user.UserManager;

/**
 * @since 3.17
 */
public class ApiUserSource
{
  private String id;

  private String name;

  public ApiUserSource(final UserManager manager) {
    this.id = manager.getSource();
    this.name = manager.getAuthenticationRealmName();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
