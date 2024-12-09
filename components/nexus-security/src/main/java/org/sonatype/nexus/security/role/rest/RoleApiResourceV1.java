package org.sonatype.nexus.security.role.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.internal.rest.SecurityApiResourceV1;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(RoleApiResourceV1.RESOURCE_URI)
public class RoleApiResourceV1
    extends RoleApiResource
{
  public static final String RESOURCE_URI = SecurityApiResourceV1.V1_RESOURCE_URI + "roles";

  @Inject
  public RoleApiResourceV1(final SecuritySystem securitySystem) {
    super(securitySystem);
  }
}
