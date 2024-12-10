package org.sonatype.nexus.security;

public final class Roles
{
  /**
   * Role ID used for NX Administrator role, used in some places to detect is user admin or not.
   */
  public static final String ADMIN_ROLE_ID = "nx-admin";

  /**
   * Role ID used for NX Anonymous rule, used in some places to detect is used anonymous or not.
   */
  public static final String ANONYMOUS_ROLE_ID = "nx-anonymous";

  private Roles() {
    // nop
  }
}
