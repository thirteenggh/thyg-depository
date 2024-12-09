package org.sonatype.nexus.security.realm;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.user.UserManager;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;

@Singleton
@Named("MockRealmA")
public class MockRealmA
    extends AuthenticatingRealm
{
  @Inject
  public MockRealmA(@Named("MockUserManagerA") UserManager userManager) {
    this.setAuthenticationTokenClass(UsernamePasswordToken.class);
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException
  {
    // only allow jcoder/jcoder

    UsernamePasswordToken userpass = (UsernamePasswordToken) token;
    if ("jcoder".equals(userpass.getUsername()) && "jcoder".equals(new String(userpass.getPassword()))) {
      return new SimpleAuthenticationInfo(userpass.getUsername(), new String(userpass.getPassword()), this.getName());
    }

    return null;
  }

  @Override
  public String getName() {
    return "MockRealmA";
  }
}
