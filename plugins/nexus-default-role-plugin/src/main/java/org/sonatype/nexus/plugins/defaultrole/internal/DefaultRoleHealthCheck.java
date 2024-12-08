package org.sonatype.nexus.plugins.defaultrole.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.plugins.defaultrole.DefaultRoleRealm;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.realm.RealmManager;
import org.sonatype.nexus.security.role.Role;

import com.codahale.metrics.health.HealthCheck;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.security.user.UserManager.DEFAULT_SOURCE;

/**
 * Will throw up a health check error when the default role in the realm isn't available
 *
 * @since 3.22
 */
@Named("默认角色域")
@Singleton
public class DefaultRoleHealthCheck
    extends HealthCheck
{
  private final RealmManager realmManager;

  private final DefaultRoleRealm defaultRoleRealm;

  private final SecuritySystem securitySystem;

  @Inject
  public DefaultRoleHealthCheck(
      final RealmManager realmManager,
      final DefaultRoleRealm defaultRoleRealm,
      final SecuritySystem securitySystem)
  {
    this.realmManager = checkNotNull(realmManager);
    this.defaultRoleRealm = checkNotNull(defaultRoleRealm);
    this.securitySystem = checkNotNull(securitySystem);
  }

  @Override
  protected Result check() throws Exception {
    if (!realmManager.isRealmEnabled(DefaultRoleRealm.NAME)) {
      return Result.healthy("默认角色域未使用。");
    }

    if (defaultRoleRealm.getRole() == null) {
      return Result.unhealthy("默认角色域已启用但未配置。");
    }

    Role matched = securitySystem.listRoles(DEFAULT_SOURCE).stream()
        .filter(role -> role.getRoleId().equals(defaultRoleRealm.getRole())).findFirst().orElse(null);

    if (matched == null) {
      return Result
          .unhealthy("默认角色域配置为使用不存在的角色%s。", defaultRoleRealm.getRole());
    }

    return Result.healthy("默认角色域配置为使用角色%s。", defaultRoleRealm.getRole());
  }
}
