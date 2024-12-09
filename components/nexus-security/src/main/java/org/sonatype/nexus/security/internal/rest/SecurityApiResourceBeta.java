package org.sonatype.nexus.security.internal.rest;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.rest.APIConstants;
import org.sonatype.nexus.security.user.UserManager;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.rest.APIConstants.BETA_API_PREFIX;

/**
 * @since 3.26
 * @deprecated beta prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX} instead
 */
@Api(hidden = true)
@Named
@Singleton
@Path(SecurityApiResourceBeta.BETA_RESOURCE_URI)
@Deprecated
public class SecurityApiResourceBeta
    extends SecurityApiResource
{
  public static final String BETA_RESOURCE_URI = BETA_API_PREFIX + "/security/";

  @Inject
  public SecurityApiResourceBeta(final Map<String, UserManager> userManagers) {
    super(userManagers);
  }
}
