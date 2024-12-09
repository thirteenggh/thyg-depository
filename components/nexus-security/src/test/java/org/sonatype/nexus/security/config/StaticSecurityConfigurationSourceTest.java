package org.sonatype.nexus.security.config;

import java.io.IOException;

import org.sonatype.nexus.security.AbstractSecurityTest;

import org.apache.shiro.authc.credential.PasswordService;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StaticSecurityConfigurationSourceTest
    extends AbstractSecurityTest
{
  private PasswordService passwordService = mock(PasswordService.class);

  private AdminPasswordFileManager adminPasswordFileManager = mock(AdminPasswordFileManager.class);

  private StaticSecurityConfigurationSource underTest;

  @Before
  public void setup() throws Exception {
    underTest = new StaticSecurityConfigurationSource(passwordService, adminPasswordFileManager, true);
    when(passwordService.encryptPassword(any())).thenReturn("encrypted");
    when(adminPasswordFileManager.readFile()).thenReturn(null);
  }

  @Test
  public void testGetConfiguration_argNonRandom() throws IOException {
    underTest = new StaticSecurityConfigurationSource(passwordService, adminPasswordFileManager, false);

    SecurityConfiguration configuration = underTest.getConfiguration();
    CUser user = configuration.getUser("admin");
    assertThat(user.getPassword(), is("encrypted"));
    verify(passwordService).encryptPassword("admin123");
    verify(adminPasswordFileManager, never()).writeFile(any());
  }

  @Test
  public void testGetConfiguration_randomGeneration() throws IOException {
    when(adminPasswordFileManager.readFile()).thenReturn(null);
    SecurityConfiguration configuration = underTest.getConfiguration();
    CUser user = configuration.getUser("admin");
    assertThat(user.getPassword(), is("encrypted"));
    verify(passwordService).encryptPassword(any());
    verify(adminPasswordFileManager).writeFile(any());
  }

  @Test
  public void testGetConfiguration_randomGenerationWriteFails() throws IOException {
    when(adminPasswordFileManager.readFile()).thenReturn(null);
    when(adminPasswordFileManager.writeFile(any())).thenReturn(false);
    SecurityConfiguration configuration = underTest.getConfiguration();
    CUser user = configuration.getUser("admin");
    assertThat(user.getPassword(), is("encrypted"));
    verify(passwordService).encryptPassword("admin123");
  }

  @Test
  public void testGetConfiguration_adminUserStatusCheck() {
    SecurityConfiguration configuration = underTest.getConfiguration();
    CUser user = configuration.getUser("admin");
    assertThat(user.getStatus(), is(CUser.STATUS_CHANGE_PASSWORD));
  }

  @Test
  public void testGetConfiguration_adminUserStatusCheckNonRandom() {
    underTest = new StaticSecurityConfigurationSource(passwordService, adminPasswordFileManager, false);
    SecurityConfiguration configuration = underTest.getConfiguration();
    CUser user = configuration.getUser("admin");
    assertThat(user.getStatus(), is(CUser.STATUS_ACTIVE));
  }

  /*
   * this test ensures that we don't inadvertently overwrite the serialized password from the first run
   */
  @Test
  public void testGetConfiguration_reloads() throws IOException {
    when(adminPasswordFileManager.readFile()).thenReturn(null).thenReturn("password");
    underTest.getConfiguration();
    underTest.getConfiguration();

    //should only write once
    verify(adminPasswordFileManager).writeFile(any());
  }
}
