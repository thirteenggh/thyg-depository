package org.sonatype.nexus.security.authz;

import org.sonatype.nexus.security.privilege.NoSuchPrivilegeException;
import org.sonatype.nexus.security.privilege.Privilege;
import org.sonatype.nexus.security.role.NoSuchRoleException;
import org.sonatype.nexus.security.role.Role;

/**
 * Read-only {@link AuthorizationManager}, which just throws exceptions for all the write methods.
 *
 * Any call to theses methods should be guarded by {@code #supportsWrite}.
 */
public abstract class AbstractReadOnlyAuthorizationManager
    implements AuthorizationManager
{
  /**
   * @return Always {@code false}
   */
  @Override
  public boolean supportsWrite() {
    return false;
  }

  @Override
  public Privilege addPrivilege(final Privilege privilege) {
    throw unsupported();
  }

  @Override
  public Role addRole(final Role role) {
    throw unsupported();
  }

  @Override
  public void deletePrivilege(final String privilegeId) throws NoSuchPrivilegeException {
    throw unsupported();
  }

  @Override
  public void deleteRole(final String roleId) throws NoSuchRoleException {
    throw unsupported();
  }

  @Override
  public Privilege updatePrivilege(final Privilege privilege) throws NoSuchPrivilegeException {
    throw unsupported();
  }

  @Override
  public Role updateRole(final Role role) throws NoSuchRoleException {
    throw unsupported();
  }

  private IllegalStateException unsupported() {
    // TODO: Should probably use UnsupportedOperationException
    throw new IllegalStateException("AuthorizationManager: '" + getSource() + "' does not support write operations.");
  }
}
