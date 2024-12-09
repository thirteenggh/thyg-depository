package org.sonatype.nexus.script.plugin.internal.rest;

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
@Path(ScriptPrivilegeApiResourceV1.RESOURCE_URI)
public class ScriptPrivilegeApiResourceV1
    extends ScriptPrivilegeApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceV1.V1_RESOURCE_URI + "privileges";

  @Inject
  public ScriptPrivilegeApiResourceV1(
      final SecuritySystem securitySystem,
      final Map<String, PrivilegeDescriptor> privilegeDescriptors)
  {
    super(securitySystem, privilegeDescriptors);
  }
}
