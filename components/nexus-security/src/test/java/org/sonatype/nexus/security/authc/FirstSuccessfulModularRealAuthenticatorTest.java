package org.sonatype.nexus.security.authc;

import com.google.common.collect.Lists;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FirstSuccessfulModularRealAuthenticatorTest
{
  private FirstSuccessfulModularRealmAuthenticator firstSuccessfulModularRealmAuthenticator;

  @Before
  public void init() {
    firstSuccessfulModularRealmAuthenticator =
        new FirstSuccessfulModularRealmAuthenticator();
  }

  @Test
  public void testMultiRealmInvalidCredentials() {
    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("username", "password");

    Realm realmOne = mock(Realm.class);
    Realm realmTwo = mock(Realm.class);

    when(realmOne.supports(usernamePasswordToken)).thenReturn(true);
    when(realmTwo.supports(usernamePasswordToken)).thenReturn(true);

    when(realmOne.getAuthenticationInfo(usernamePasswordToken)).thenThrow(new IncorrectCredentialsException());
    when(realmTwo.getAuthenticationInfo(usernamePasswordToken)).thenThrow(new IncorrectCredentialsException());

    try {
      firstSuccessfulModularRealmAuthenticator
          .doMultiRealmAuthentication(Lists.newArrayList(realmOne, realmTwo), usernamePasswordToken);
    }
    catch (NexusAuthenticationException e) {
      assertThat(e.getAuthenticationFailureReasons(), containsInAnyOrder(AuthenticationFailureReason.INCORRECT_CREDENTIALS));
    }
  }

  @Test
  public void testMultiRealmMultipleFailures() {
    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("username", "password");

    Realm realmOne = mock(Realm.class);
    Realm realmTwo = mock(Realm.class);

    when(realmOne.supports(usernamePasswordToken)).thenReturn(true);
    when(realmTwo.supports(usernamePasswordToken)).thenReturn(true);

    when(realmOne.getAuthenticationInfo(usernamePasswordToken)).thenThrow(new IncorrectCredentialsException());
    when(realmTwo.getAuthenticationInfo(usernamePasswordToken)).thenThrow(new UnknownAccountException());

    try {
      firstSuccessfulModularRealmAuthenticator
          .doMultiRealmAuthentication(Lists.newArrayList(realmOne, realmTwo), usernamePasswordToken);
    }
    catch (NexusAuthenticationException e) {
      assertThat(e.getAuthenticationFailureReasons(), containsInAnyOrder(AuthenticationFailureReason.INCORRECT_CREDENTIALS, AuthenticationFailureReason.USER_NOT_FOUND));
    }
  }

  @Test
  public void testSingleRealmFailureIsStillSuccessful() {
    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("username", "password");

    Realm realmOne = mock(Realm.class);
    Realm realmTwo = mock(Realm.class);

    when(realmOne.supports(usernamePasswordToken)).thenReturn(true);
    when(realmTwo.supports(usernamePasswordToken)).thenReturn(true);

    when(realmOne.getAuthenticationInfo(usernamePasswordToken)).thenThrow(new IncorrectCredentialsException());
    when(realmTwo.getAuthenticationInfo(usernamePasswordToken)).thenReturn(new SimpleAccount());

    firstSuccessfulModularRealmAuthenticator
        .doMultiRealmAuthentication(Lists.newArrayList(realmOne, realmTwo), usernamePasswordToken);
  }
}
