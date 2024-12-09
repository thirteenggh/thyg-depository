package org.sonatype.nexus.repository.security.rest;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.internal.rest.SecurityApiResourceV1;
import org.sonatype.nexus.security.privilege.PrivilegeDescriptor;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(RepositoryPrivilegeApiResourceV1.RESOURCE_URI)
public class RepositoryPrivilegeApiResourceV1
    extends RepositoryPrivilegeApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceV1.V1_RESOURCE_URI + "privileges";

  @Inject
  public RepositoryPrivilegeApiResourceV1(
      final SecuritySystem securitySystem,
      final Map<String, PrivilegeDescriptor> privilegeDescriptors)
  {
    super(securitySystem, privilegeDescriptors);
  }
}
