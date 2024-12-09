package org.sonatype.nexus.security.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.security.config.CUser;
import org.sonatype.nexus.security.config.SecurityConfigurationManager;
import org.sonatype.nexus.security.config.memory.MemoryCUser;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class AuthenticatingRealmImplTest
    extends TestSupport
{
  private static final String TEST_USERNAME = "testUser";

  private static final String TEST_PASSWORD = "admin123";

  private static final String LEGACY_PASSWORD_HASH = "f865b53623b121fd34ee5426c792e5c33af8c227";

  @Mock
  private SecurityConfigurationManager configuration;

  private CUser testUser = new MemoryCUser();

  private AuthenticatingRealmImpl underTest;

  @Before
  public void setUp() throws Exception {

    testUser.setId(TEST_USERNAME);
    testUser.setStatus(CUser.STATUS_ACTIVE);
    testUser.setPassword(LEGACY_PASSWORD_HASH);

    // read must pass back detached copy to avoid side-effects in test
    when(configuration.readUser(TEST_USERNAME)).thenAnswer((inv) -> testUser.clone());

    // can't use argument captor because argument is mutated after call
    doAnswer((inv) -> {
      // capture password from the updated user at the time of the call
      testUser.setPassword(((CUser) inv.getArguments()[0]).getPassword());
      return null;
    }).when(configuration).updateUser(any());

    underTest = new AuthenticatingRealmImpl(configuration,
        new DefaultSecurityPasswordService(new LegacyNexusPasswordService()));
  }

  @Test
  public void testLegacyPasswordIsReHashed() throws Exception {
    assertThat(testUser.getPassword(), is(LEGACY_PASSWORD_HASH));
    underTest.getAuthenticationInfo(new UsernamePasswordToken(TEST_USERNAME, TEST_PASSWORD));
    assertThat(testUser.getPassword(), startsWith("$shiro1$SHA-512$1024$"));
  }
}
