package org.sonatype.nexus.repository.security.rest;

import java.util.Collection;

import org.sonatype.nexus.repository.security.RepositoryAdminPrivilegeDescriptor;
import org.sonatype.nexus.security.privilege.Privilege;
import org.sonatype.nexus.security.privilege.rest.PrivilegeAction;

/**
 * @since 3.19
 */
public class ApiPrivilegeRepositoryAdmin
    extends ApiPrivilegeWithRepository
{
  /**
   * for deserialization
   */
  private ApiPrivilegeRepositoryAdmin() {
    super(RepositoryAdminPrivilegeDescriptor.TYPE);
  }

  public ApiPrivilegeRepositoryAdmin(final String name,
                                     final String description,
                                     final boolean readOnly,
                                     final String format,
                                     final String repository,
                                     final Collection<PrivilegeAction> actions)
  {
    super(RepositoryAdminPrivilegeDescriptor.TYPE, name, description, readOnly, format, repository, actions);
  }

  public ApiPrivilegeRepositoryAdmin(final Privilege privilege) {
    super(privilege);
  }
}
