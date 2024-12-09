package org.sonatype.nexus.security.anonymous.rest;

import java.io.IOException;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.rest.ValidationErrorsException;
import org.sonatype.nexus.security.TestAnonymousConfiguration;
import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;
import org.sonatype.nexus.security.anonymous.AnonymousManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.fest.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnonymousAccessApiResourceTest
    extends TestSupport
{
  @Mock
  private AnonymousManager anonymousManager;

  @Mock
  private RealmSecurityManager realmSecurityManager;

  private AnonymousAccessApiResource underTest;

  private AnonymousConfiguration initialAnonymousConfiguration = new TestAnonymousConfiguration();

  @Before
  public void setup() {
    initialAnonymousConfiguration.setEnabled(true);
    initialAnonymousConfiguration.setUserId(AnonymousConfiguration.DEFAULT_USER_ID);
    initialAnonymousConfiguration.setRealmName(AnonymousConfiguration.DEFAULT_REALM_NAME);

    when(anonymousManager.newConfiguration()).thenReturn(initialAnonymousConfiguration);
    when(anonymousManager.getConfiguration()).thenReturn(initialAnonymousConfiguration);

    Realm realm =  mock(Realm.class);
    when(realm.getName()).thenReturn(AnonymousConfiguration.DEFAULT_REALM_NAME);
    when(realmSecurityManager.getRealms()).thenReturn(Lists.newArrayList(realm));

    underTest = new AnonymousAccessApiResource(anonymousManager, realmSecurityManager);
  }

  @Test
  public void testGet() {
    assertThat(underTest.read(), is(new AnonymousAccessSettingsXO(initialAnonymousConfiguration)));
  }

  @Test
  public void testUpdate() {
    AnonymousConfiguration newConfiguration = new TestAnonymousConfiguration();
    newConfiguration.setRealmName(AnonymousConfiguration.DEFAULT_REALM_NAME);
    newConfiguration.setUserId(AnonymousConfiguration.DEFAULT_USER_ID);
    newConfiguration.setEnabled(false);

    AnonymousAccessSettingsXO xo = new AnonymousAccessSettingsXO(newConfiguration);
    when(anonymousManager.getConfiguration()).thenReturn(newConfiguration);
    assertThat(underTest.update(xo), is(new AnonymousAccessSettingsXO(newConfiguration)));
  }

  @Test(expected = ValidationErrorsException.class)
  public void testInvalidRealm() {
    AnonymousConfiguration newConfiguration = new TestAnonymousConfiguration();
    newConfiguration.setRealmName("invalidRealmName");
    newConfiguration.setUserId(AnonymousConfiguration.DEFAULT_USER_ID);
    newConfiguration.setEnabled(false);

    underTest.update(new AnonymousAccessSettingsXO(newConfiguration));
  }

  @Test
  public void testDeserialize() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    String value = "{\"enabled\": true }";
    AnonymousAccessSettingsXO settings = mapper.readValue(value.getBytes(), AnonymousAccessSettingsXO.class);
    assertThat(settings.isEnabled(), is(true));
  }
}
