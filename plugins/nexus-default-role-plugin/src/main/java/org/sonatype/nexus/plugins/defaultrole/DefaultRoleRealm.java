package org.sonatype.nexus.plugins.defaultrole;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.anonymous.AnonymousHelper;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.eclipse.sisu.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Realm that adds the specified role to all authenticated users
 *
 * @since 3.22
 */
@Named(DefaultRoleRealm.NAME)
@Singleton
@Description("Default Role Realm")
public class DefaultRoleRealm
    extends AuthorizingRealm
{
  private static final Logger log = LoggerFactory.getLogger(DefaultRoleRealm.class);

  public static final String NAME = "DefaultRole";

  private String role;

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
    return maybeGrantRole(principals);
  }

  private AuthorizationInfo maybeGrantRole(final PrincipalCollection principals) {
    if (role != null) {
      // only attempt to apply default role if user is not anonymous
      if (!AnonymousHelper.isAnonymous(principals)) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(role);
        log.debug("Granting {} role to {}", role, principals);
        return info;
      }
    }

    return null;
  }

  /**
   * @throws UnsupportedOperationException Authentication is not supported
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken token) throws AuthenticationException {
    throw new UnsupportedOperationException();
  }

  @Nullable
  public String getRole() {
    return role;
  }

  public void setRole(@Nullable final String role) {
    this.role = role;
  }
}
