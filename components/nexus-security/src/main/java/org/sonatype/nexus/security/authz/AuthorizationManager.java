package org.sonatype.nexus.security.authz;

import java.util.Set;

import org.sonatype.nexus.security.privilege.NoSuchPrivilegeException;
import org.sonatype.nexus.security.privilege.Privilege;
import org.sonatype.nexus.security.role.Role;

/**
 * Authorization manager.
 */
public interface AuthorizationManager
{
  /**
   * The Id if this AuthorizationManager;
   */
  String getSource();

  /**
   * If this AuthorizationManager is writable.
   */
  boolean supportsWrite();

  /**
   * Returns the all Roles from this AuthorizationManager. NOTE: this call could be slow when coming from an external
   * source (i.e. a database) TODO: Consider removing this method.
   */
  Set<Role> listRoles();

  /**
   * Returns a Role base on an Id.
   */
  Role getRole(String roleId);

  /**
   * Adds a role to this AuthorizationManager.
   */
  Role addRole(Role role);

  /**
   * Updates a role in this AuthorizationManager.
   */
  Role updateRole(Role role);

  /**
   * Removes a role in this AuthorizationManager.
   */
  void deleteRole(String roleId);

  // Privilege CRUDS

  /**
   * Returns the all Privileges from this AuthorizationManager.
   */
  Set<Privilege> listPrivileges();

  /**
   * Returns a Privilege base on an Id.
   */
  Privilege getPrivilege(String privilegeId) throws NoSuchPrivilegeException;

  /**
   * Adds a Privilege to this AuthorizationManager.
   */
  Privilege addPrivilege(Privilege privilege);

  /**
   * Updates a Privilege in this AuthorizationManager.
   */
  Privilege updatePrivilege(Privilege privilege) throws NoSuchPrivilegeException;

  /**
   * Removes a Privilege in this AuthorizationManager.
   */
  void deletePrivilege(String privilegeId) throws NoSuchPrivilegeException;
}
