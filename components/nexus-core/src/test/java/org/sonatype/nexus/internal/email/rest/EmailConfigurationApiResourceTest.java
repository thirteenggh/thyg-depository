package org.sonatype.nexus.internal.email.rest;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.email.EmailConfiguration;
import org.sonatype.nexus.email.EmailManager;

import org.apache.commons.mail.EmailException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmailConfigurationApiResourceTest
    extends TestSupport
{
  @Mock
  private EmailManager emailManager;

  @Mock
  private EmailConfiguration emailConfiguration;

  private EmailConfigurationApiResource underTest;

  @Before
  public void setup() {
    underTest = new EmailConfigurationApiResource(emailManager);
  }

  @Test
  public void getUnconfiguredEmailConfigurationHandlesNullDefaultConfiguration() {
    ApiEmailConfiguration response = underTest.getEmailConfiguration();

    assertThat(response.getFromAddress(), is(nullValue()));
    assertThat(response.getHost(), is(nullValue()));
    assertThat(response.getPassword(), is(nullValue()));
    assertThat(response.getPort(), is(nullValue()));
    assertThat(response.getSubjectPrefix(), is(nullValue()));
    assertThat(response.getUsername(), is(nullValue()));
    assertThat(response.isEnabled(), is(false));
    assertThat(response.isNexusTrustStoreEnabled(), is(false));
    assertThat(response.isSslOnConnectEnabled(), is(false));
    assertThat(response.isSslServerIdentityCheckEnabled(), is(false));
    assertThat(response.isStartTlsEnabled(), is(false));
    assertThat(response.isStartTlsRequired(), is(false));
  }

  @Test
  public void getEmailConfigurationObfuscatesThePassword() {
    when(emailConfiguration.getPassword()).thenReturn("testpassword");
    when(emailManager.getConfiguration()).thenReturn(emailConfiguration);

    ApiEmailConfiguration response = underTest.getEmailConfiguration();

    assertThat(response.getPassword(), is(nullValue()));
  }

  @Test
  public void setEmailConfigurationSetsTheNewConfiguration() {
    EmailConfiguration newConfiguration = mock(EmailConfiguration.class);
    ApiEmailConfiguration request = new ApiEmailConfiguration();
    request.setEnabled(true);
    request.setPassword("testPassword");

    when(emailManager.newConfiguration()).thenReturn(newConfiguration);

    underTest.setEmailConfiguration(request);

    verify(newConfiguration).setPassword(request.getPassword());
    verify(newConfiguration).setEnabled(true);
    verify(emailManager).setConfiguration(newConfiguration);
  }

  @Test
  public void setEmailConfigurationKeepsTheOriginalPassword() {
    EmailConfiguration newConfiguration = mock(EmailConfiguration.class);
    when(emailConfiguration.getPassword()).thenReturn("testpassword");
    when(emailManager.getConfiguration()).thenReturn(emailConfiguration);
    when(emailManager.newConfiguration()).thenReturn(newConfiguration);

    ApiEmailConfiguration request = new ApiEmailConfiguration();
    request.setEnabled(true);
    request.setPassword("");

    underTest.setEmailConfiguration(request);

    verify(newConfiguration).setPassword(emailConfiguration.getPassword());
    verify(newConfiguration).setEnabled(true);
    verify(emailManager).setConfiguration(newConfiguration);
  }

  @Test
  public void testEmailConfigurationSendsTestEmail() throws EmailException {
    when(emailConfiguration.isEnabled()).thenReturn(true);
    when(emailManager.getConfiguration()).thenReturn(emailConfiguration);
    String destinationAddress = "test@example.com";

    underTest.testEmailConfiguration(destinationAddress);

    verify(emailManager).sendVerification(emailConfiguration, destinationAddress);
  }
}
