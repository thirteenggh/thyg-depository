package org.sonatype.nexus.security.privilege.rest;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.rest.APIConstants;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.internal.rest.SecurityApiResourceBeta;
import org.sonatype.nexus.security.privilege.PrivilegeDescriptor;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.security.privilege.rest.PrivilegeApiResourceBeta.RESOURCE_URI;

/**
 * @since 3.26
 * @deprecated beta prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX} instead
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class PrivilegeApiResourceBeta
    extends PrivilegeApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceBeta.BETA_RESOURCE_URI + "privileges";

  @Inject
  public PrivilegeApiResourceBeta(
      final SecuritySystem securitySystem,
      final Map<String, PrivilegeDescriptor> privilegeDescriptors)
  {
    super(securitySystem, privilegeDescriptors);
  }
}
