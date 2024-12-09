package org.sonatype.nexus.security.internal.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.rest.APIConstants;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.config.AdminPasswordFileManager;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.security.internal.rest.UserApiResourceBeta.RESOURCE_URI;

/**
 * @since 3.26
 * @deprecated beta prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX} instead
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class UserApiResourceBeta
    extends UserApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceBeta.BETA_RESOURCE_URI + "users/";

  @Inject
  public UserApiResourceBeta(
      final SecuritySystem securitySystem,
      final AdminPasswordFileManager adminPasswordFileManager)
  {
    super(securitySystem, adminPasswordFileManager);
  }
}
