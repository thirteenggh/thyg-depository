package org.sonatype.nexus.security.role.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.rest.APIConstants;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.internal.rest.SecurityApiResourceBeta;

import io.swagger.annotations.Api;

/**
 * @since 3.26
 * @deprecated beta prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX} instead
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RoleApiResourceBeta.RESOURCE_URI)
@Deprecated
public class RoleApiResourceBeta
    extends RoleApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceBeta.BETA_RESOURCE_URI + "roles";

  @Inject
  public RoleApiResourceBeta(final SecuritySystem securitySystem) {
    super(securitySystem);
  }
}
