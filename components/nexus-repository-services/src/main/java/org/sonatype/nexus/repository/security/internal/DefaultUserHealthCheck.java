package org.sonatype.nexus.repository.security.internal;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.internal.AuthenticatingRealmImpl;
import org.sonatype.nexus.security.realm.RealmManager;

import com.codahale.metrics.health.HealthCheck;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Check if the default user can be used to authenticate.
 */
@Named("默认管理员凭据")
@Singleton
public class DefaultUserHealthCheck
    extends HealthCheck
{
  private static final Logger log = LoggerFactory.getLogger(DefaultUserHealthCheck.class);

  static final String ERROR_MESSAGE = "默认管理员凭据尚未更改。强烈建议更改默认管理员密码。";

  private final RealmManager realmManager;

  private final RealmSecurityManager realmSecurityManager;

  @Inject
  public DefaultUserHealthCheck(final RealmManager realmManager, final RealmSecurityManager realmSecurityManager)
  {
    this.realmManager = checkNotNull(realmManager);
    this.realmSecurityManager = checkNotNull(realmSecurityManager);
  }

  @Override
  protected Result check() {
    if (!realmManager.isRealmEnabled(AuthenticatingRealmImpl.NAME)) {
      return Result.healthy();
    }

    Optional<Realm> realm = realmSecurityManager.getRealms().stream()
        .filter(r -> r.getName().equals(AuthenticatingRealmImpl.NAME)).findFirst();

    try {
      if (realm.map(r -> r.getAuthenticationInfo(new UsernamePasswordToken("admin", "admin123"))).isPresent()) {
        return Result.unhealthy(ERROR_MESSAGE);
      }
    }
    catch (AuthenticationException e) {
      log.trace("找不到 admin/admin123 用户", e);
    }
    return Result.healthy();
  }
}
