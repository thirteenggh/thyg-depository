package org.sonatype.nexus.plugins.defaultrole.internal;

import java.util.Map;

import org.sonatype.nexus.capability.CapabilityConfigurationSupport;

/**
 * Simple configuration for {@link DefaultRoleCapability} containing a single roleId.
 *
 * @since 3.22
 */
public class DefaultRoleCapabilityConfiguration
    extends CapabilityConfigurationSupport
{
  public static final String P_ROLE = "role";

  private String role;

  public DefaultRoleCapabilityConfiguration(final Map<String, String> properties) {
    role = properties.get(P_ROLE);
  }

  public String getRole() {
    return role;
  }

  public void setRole(final String role) {
    this.role = role;
  }
}
