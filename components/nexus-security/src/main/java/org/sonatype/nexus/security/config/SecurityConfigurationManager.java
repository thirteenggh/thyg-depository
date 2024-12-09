package org.sonatype.nexus.security.config;

import java.util.List;
import java.util.Set;

import org.sonatype.nexus.security.user.NoSuchRoleMappingException;
import org.sonatype.nexus.security.user.UserNotFoundException;

/**
 * The ConfigurationManager is a facade in front of the security modello model. It supports CRUD operations for
 * users/roles/privileges and user to role mappings.
 *
 * Any direct calls to write-based ConfigurationManager methods will throw an IllegalStateException, as they
 * cannot be used directly in a thread-safe manner
 *
 * Direct calls to read-based ConfigurationManager methods can be called in a thread-safe manner. However, operations
 * that require multiple read-based calls should be encapsulated into an action and executed via the runRead method.
 */
public interface SecurityConfigurationManager
{
  //
  // Users
  //

  /**
   * Retrieve all users
   */
  List<CUser> listUsers();

  /**
   * Create a new user.
   *
   * Note: the underlying implementation may throw an exception if the instance of {@link CUser}
   * was not obtained by calling {@link #newUser}.
   */
  void createUser(CUser user, Set<String> roles);

  /**
   * Create a new user and sets the password.
   *
   * Note: the underlying implementation may throw an exception if the instance of {@link CUser}
   * was not obtained by calling {@link #newUser}.
   */
  void createUser(CUser user, String password, Set<String> roles);

  /**
   * Create a new instance of {@link CUser} suitable for use with the underlying store
   *
   * @since 3.20
   */
  CUser newUser();

  /**
   * Retrieve an existing user
   */
  CUser readUser(String id) throws UserNotFoundException;

  /**
   * Update an existing user. Roles are unchanged
   *
   * Note: the underlying implementation may throw an exception if the instance of {@link CUser}
   * was not obtained from this configuration.
   *
   * @param user to update
   */
  void updateUser(CUser user) throws UserNotFoundException;

  /**
   * Update an existing user and their roles
   *
   * Note: the underlying implementation may throw an exception if the instance of {@link CUser}
   * was not obtained from this configuration.
   */
  void updateUser(CUser user, Set<String> roles) throws UserNotFoundException;

  /**
   * Delete an existing user
   */
  void deleteUser(String id) throws UserNotFoundException;

  //
  // Roles
  //

  /**
   * Retrieve all roles
   */
  List<CRole> listRoles();

  /**
   * Create a new role
   */
  void createRole(CRole role);

  /**
   * Create a new instance of {@link CRole} suitable for use with the underlying store
   *
   * @since 3.20
   */
  CRole newRole();

  /**
   * Retrieve an existing role
   */
  CRole readRole(String id);

  /**
   * Update an existing role
   */
  void updateRole(CRole role);

  /**
   * Delete an existing role
   */
  void deleteRole(String id);

  //
  // Privileges
  //

  /**
   * Retrieve all privileges
   */
  List<CPrivilege> listPrivileges();

  /**
   * Create a new privilege
   */
  void createPrivilege(CPrivilege privilege);

  /**
   * Create a new instance of {@link CRole} suitable for use with the underlying store
   *
   * @since 3.21
   */
  CPrivilege newPrivilege();

  /**
   * Retrieve an existing privilege
   */
  CPrivilege readPrivilege(String id);

  /**
   * Update an existing privilege
   */
  void updatePrivilege(CPrivilege privilege);

  /**
   * Delete an existing privilege
   */
  void deletePrivilege(String id);

  void cleanRemovedPrivilege(String privilegeId);

  //
  // User-role mapping
  //

  void createUserRoleMapping(CUserRoleMapping userRoleMapping);

  /**
   * Create a new instance of {@link CUserRoleMapping} suitable for use with the underlying store
   *
   * @since 3.20
   */
  CUserRoleMapping newUserRoleMapping();

  void updateUserRoleMapping(CUserRoleMapping userRoleMapping) throws NoSuchRoleMappingException;

  CUserRoleMapping readUserRoleMapping(String userId, String source) throws NoSuchRoleMappingException;

  List<CUserRoleMapping> listUserRoleMappings();

  void deleteUserRoleMapping(String userId, String source) throws NoSuchRoleMappingException;
}
