package org.sonatype.nexus.repository.security.rest;

import java.util.Collection;

import org.sonatype.nexus.repository.security.RepositoryViewPrivilegeDescriptor;
import org.sonatype.nexus.security.privilege.Privilege;
import org.sonatype.nexus.security.privilege.rest.PrivilegeAction;

/**
 * @since 3.19
 */
public class ApiPrivilegeRepositoryViewRequest
    extends ApiPrivilegeWithRepositoryRequest
{
  /**
   * for deserialization
   */
  private ApiPrivilegeRepositoryViewRequest() {
    super();
  }

  public ApiPrivilegeRepositoryViewRequest(final String name,
                                           final String description,
                                           final String format,
                                           final String repository,
                                           final Collection<PrivilegeAction> actions)
  {
    super(name, description, format, repository, actions);
  }

  public ApiPrivilegeRepositoryViewRequest(final Privilege privilege) {
    super(privilege);
  }

  @Override
  protected Privilege doAsPrivilege(final Privilege privilege) {
    super.doAsPrivilege(privilege);
    privilege.setType(RepositoryViewPrivilegeDescriptor.TYPE);
    return privilege;
  }
}
