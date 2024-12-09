package org.sonatype.nexus.security.realm;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.security.role.ExternalRoleMappedTest;
import org.sonatype.nexus.security.role.RoleIdentifier;
import org.sonatype.nexus.security.user.UserManager;
import org.sonatype.nexus.security.user.UserNotFoundException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * @see ExternalRoleMappedTest
 */
public class MockRealm
    extends AuthorizingRealm
{
  public static final String NAME = "Mock";

  private final UserManager userManager;

  @Inject
  public MockRealm(@Named("Mock") UserManager userManager) {
    this.userManager = userManager;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String userId = principals.getPrimaryPrincipal().toString();

    Set<String> roles = new HashSet<String>();
    try {
      for (RoleIdentifier roleIdentifier : userManager.getUser(userId).getRoles()) {
        roles.add(roleIdentifier.getRoleId());
      }
    }
    catch (UserNotFoundException e) {
      return null;
    }

    return new SimpleAuthorizationInfo(roles);
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    UsernamePasswordToken upToken = (UsernamePasswordToken) token;

    String password = new String(upToken.getPassword());
    String userId = upToken.getUsername();

    // username == password
    try {
      if (userId.endsWith(password) && userManager.getUser(userId) != null) {
        return new SimpleAuthenticationInfo(new SimplePrincipalCollection(token.getPrincipal(),
            this.getName()), userId);
      }
      else {
        throw new IncorrectCredentialsException("User [" + userId + "] bad credentials.");
      }
    }
    catch (UserNotFoundException e) {
      throw new UnknownAccountException("User [" + userId + "] not found.");
    }
  }

  @Override
  public String getName() {
    return "Mock";
  }
}
