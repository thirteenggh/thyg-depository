package org.sonatype.nexus.repository.security.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.security.internal.AuthenticatingRealmImpl;
import org.sonatype.nexus.security.realm.RealmManager;

import com.codahale.metrics.health.HealthCheck.Result;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static java.util.Collections.singleton;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultUserHealthCheckTest
    extends TestSupport
{
  @Mock
  private RealmManager realmManager;

  @Mock
  private RealmSecurityManager realmSecurityManager;

  @InjectMocks
  private DefaultUserHealthCheck defaultUserHealthCheck;

  @Test
  public void checkIsHealthyWhenRealmIsDisabled() {
    when(realmManager.isRealmEnabled(AuthenticatingRealmImpl.NAME)).thenReturn(false);

    Result result = defaultUserHealthCheck.check();

    assertThat(result.isHealthy(), is(true));
  }

  @Test
  public void checkIsHealthyWhenRealmIsEnabled() {
    Realm realm = mock(Realm.class);
    when(realmManager.isRealmEnabled(AuthenticatingRealmImpl.NAME)).thenReturn(true);
    when(realmSecurityManager.getRealms()).thenReturn(singleton(realm));
    when(realm.getName()).thenReturn(AuthenticatingRealmImpl.NAME);
    when(realm.getAuthenticationInfo(any(UsernamePasswordToken.class))).thenThrow(AuthenticationException.class);

    Result result = defaultUserHealthCheck.check();

    assertThat(result.isHealthy(), is(true));
  }

  @Test
  public void checkIsUnhealthy() {
    Realm realm = mock(Realm.class);
    when(realmManager.isRealmEnabled(AuthenticatingRealmImpl.NAME)).thenReturn(true);
    when(realmSecurityManager.getRealms()).thenReturn(singleton(realm));
    when(realm.getName()).thenReturn(AuthenticatingRealmImpl.NAME);
    when(realm.getAuthenticationInfo(any(UsernamePasswordToken.class))).thenReturn(mock(AuthenticationInfo.class));

    Result result = defaultUserHealthCheck.check();

    assertThat(result.isHealthy(), is(false));
    assertThat(result.getMessage(), is(DefaultUserHealthCheck.ERROR_MESSAGE));
  }
}
