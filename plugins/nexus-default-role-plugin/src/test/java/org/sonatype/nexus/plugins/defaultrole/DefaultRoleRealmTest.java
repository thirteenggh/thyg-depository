package org.sonatype.nexus.plugins.defaultrole;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.security.anonymous.AnonymousPrincipalCollection;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultRoleRealmTest
    extends TestSupport
{
  private DefaultRoleRealm underTest;

  @Before
  public void setup() {
    underTest = new DefaultRoleRealm();
  }

  @Test
  public void testDoGetAuthorizationInfo_notConfigured() {
    underTest.setRole(null);

    AuthorizationInfo authorizationInfo = underTest.doGetAuthorizationInfo(principals("test"));
    assertThat(authorizationInfo, nullValue());
  }

  @Test
  public void testDoGetAuthorizationInfo_authenticatedUser() {
    underTest.setRole("default-role");

    AuthorizationInfo authorizationInfo = underTest.doGetAuthorizationInfo(principals("test"));
    assertThat(authorizationInfo, notNullValue());
    assertThat(authorizationInfo.getRoles(), is(singleton("default-role")));
  }

  @Test
  public void testDoGetAuthorizationInfo_anonymousUser() {
    underTest.setRole("default-role");

    AuthorizationInfo authorizationInfo = underTest.doGetAuthorizationInfo(principals("anonymous"));
    assertThat(authorizationInfo, nullValue());
  }

  private static PrincipalCollection principals(final String userId) {
    if ("anonymous".equals(userId)) {
      return new AnonymousPrincipalCollection(userId, "realm");
    }
    return new SimplePrincipalCollection(userId, "realm");
  }
}
