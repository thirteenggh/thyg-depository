package org.sonatype.nexus.security.internal.rest;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.security.user.UserManager;

import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(SecurityApiResourceV1.V1_RESOURCE_URI)
public class SecurityApiResourceV1
    extends SecurityApiResource
{
  public static final String V1_RESOURCE_URI = V1_API_PREFIX + "/security/";

  @Inject
  public SecurityApiResourceV1(final Map<String, UserManager> userManagers) {
    super(userManagers);
  }
}
