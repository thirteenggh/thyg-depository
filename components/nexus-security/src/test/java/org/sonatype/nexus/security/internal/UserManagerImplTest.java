package org.sonatype.nexus.security.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.config.CUser;
import org.sonatype.nexus.security.config.SecurityConfigurationManager;
import org.sonatype.nexus.security.config.memory.MemoryCUser;

import org.apache.shiro.authc.credential.PasswordService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserManagerImplTest
    extends TestSupport
{
  @Mock
  EventManager eventManager;

  @Mock
  SecurityConfigurationManager securityConfigurationManager;

  @Mock
  SecuritySystem securitySystem;

  @Mock
  PasswordService passwordService;

  @Mock
  PasswordValidator passwordValidator;

  private UserManagerImpl underTest;

  @Before
  public void setup() {
    underTest = new UserManagerImpl(eventManager, securityConfigurationManager, securitySystem, passwordService,
        passwordValidator);
  }

  @Test
  public void testChangePassword() throws Exception {
    CUser user = new MemoryCUser();
    user.setStatus(CUser.STATUS_CHANGE_PASSWORD);
    user.setId("test");

    when(securityConfigurationManager.readUser("test")).thenReturn(user);

    underTest.changePassword("test", "newpass");

    assertThat(user.getStatus(), is(CUser.STATUS_ACTIVE));

    verify(passwordValidator).validate("newpass");
  }
}
