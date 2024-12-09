package org.sonatype.nexus.security.internal.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.config.AdminPasswordFileManager;

import static org.sonatype.nexus.security.internal.rest.UserApiResourceV1.RESOURCE_URI;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class UserApiResourceV1
    extends UserApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceV1.V1_RESOURCE_URI + "users/";

  @Inject
  public UserApiResourceV1(
      final SecuritySystem securitySystem,
      final AdminPasswordFileManager adminPasswordFileManager)
  {
    super(securitySystem, adminPasswordFileManager);
  }
}
