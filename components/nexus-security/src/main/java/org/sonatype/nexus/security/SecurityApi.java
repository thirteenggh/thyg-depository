package org.sonatype.nexus.security;

import java.util.List;

import org.sonatype.nexus.common.script.ScriptApi;
import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;
import org.sonatype.nexus.security.role.Role;
import org.sonatype.nexus.security.user.User;

/**
 * Security provisioning capabilities of the repository manager. 
 * @since 3.0
 */
public interface SecurityApi
    extends ScriptApi
{
  default String getName() {
    return "security";
  }

  /**
   * Set whether or not to allow anonymous access to the system.
   */
  AnonymousConfiguration setAnonymousAccess(boolean enabled);

  /**
   * Add a new User to the system.
   */
  User addUser(String id, String firstName, String lastName, String email, boolean active, String password,
               List<String> roleIds);

  /**
   * Add a new Role to the system.
   */
  Role addRole(String id, String name, String description, List<String> privileges, List<String> roles);

  /**
   * Set the Roles on a given User.
   */
  User setUserRoles(String userId, List<String> roleIds);
  
}
