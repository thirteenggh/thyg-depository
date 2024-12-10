package org.sonatype.nexus.security.user;

import java.util.Set;

import org.sonatype.nexus.security.role.RoleIdentifier;

public interface RoleMappingUserManager
    extends UserManager
{
  /**
   * Returns a list of roles for a user.
   */
  Set<RoleIdentifier> getUsersRoles(String userId, String userSource) throws UserNotFoundException;

  /**
   * Sets a users roles.
   */
  void setUsersRoles(String userId, String userSource, Set<RoleIdentifier> roleIdentifiers) throws UserNotFoundException;
}
