package org.sonatype.nexus.internal.security.model;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.security.config.CPrivilege;
import org.sonatype.nexus.security.config.CRole;
import org.sonatype.nexus.security.config.CUser;
import org.sonatype.nexus.security.config.CUserRoleMapping;
import org.sonatype.nexus.security.config.SecurityConfiguration;
import org.sonatype.nexus.security.config.SecurityConfigurationSource;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.SCHEMAS;
import static org.sonatype.nexus.common.property.SystemPropertiesHelper.getBoolean;

/**
 * Default implementation of {@link SecurityConfigurationSource}.
 *
 * @since 3.21
 */
@Named("default")
@ManagedLifecycle(phase = SCHEMAS)
@FeatureFlag(name = "nexus.datastore.enabled")
@Singleton
public class SecurityConfigurationSourceImpl
    extends StateGuardLifecycleSupport
    implements SecurityConfigurationSource
{
  private SecurityConfigurationImpl securityConfiguration;

  private SecurityConfigurationSource securityDefaults;

  @Inject
  public SecurityConfigurationSourceImpl(
      final SecurityConfigurationImpl securityConfiguration,
      @Named("static") final SecurityConfigurationSource securityDefaults)
  {
    this.securityConfiguration = checkNotNull(securityConfiguration);
    this.securityDefaults = checkNotNull(securityDefaults);
  }

  @Override
  protected void doStart() throws Exception {
    // only add defaults if we're not using Orient for config
    if (!getBoolean("nexus.orient.store.config", true)) {
      addDefaultUsers();
      addDefaultRoles();
      addDefaultPrivileges();
      addDefaultUserRoleMappings();
    }
  }

  private void addDefaultUsers() {
    log.info("Initializing default users");

    for (CUser user : securityDefaults.getConfiguration().getUsers()) {
      if (securityConfiguration.getUser(user.getId()) == null) {
        securityConfiguration.addUser(user);
      }
    }
  }

  private void addDefaultRoles() {
    log.info("Initializing default roles");

    for (CRole role : securityDefaults.getConfiguration().getRoles()) {
      if (securityConfiguration.getRole(role.getId()) == null) {
        securityConfiguration.addRole(role);
      }
    }
  }

  private void addDefaultPrivileges() {
    log.info("Initializing default privileges");

    for (CPrivilege privilege : securityDefaults.getConfiguration().getPrivileges()) {
      if (securityConfiguration.getPrivilege(privilege.getId()) == null) {
        securityConfiguration.addPrivilege(privilege);
      }
    }
  }

  private void addDefaultUserRoleMappings() {
    log.info("Initializing default user/role mappings");

    for (CUserRoleMapping mapping : securityDefaults.getConfiguration().getUserRoleMappings()) {
      if (securityConfiguration.getUserRoleMapping(mapping.getUserId(), mapping.getSource()) == null) {
        securityConfiguration.addUserRoleMapping(mapping);
      }
    }
  }

  @Override
  public SecurityConfiguration getConfiguration() {
    return securityConfiguration;
  }

  @Override
  public SecurityConfiguration loadConfiguration() {
    return securityConfiguration;
  }
}
