package org.sonatype.nexus.repository.apt.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.apt.internal.hosted.AptHostedRecipe;
import org.sonatype.nexus.repository.apt.internal.proxy.AptProxyRecipe;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.repository.view.handlers.HighAvailabilitySupportChecker;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @since 3.17
 */
public class AptRecipeTest
    extends TestSupport
{
  @Mock
  AptFormat format;

  @Mock
  HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  AptHostedRecipe hostedRecipe;

  AptProxyRecipe proxyRecipe;

  final String APT_NAME = "apt";

  @Before
  public void setUp() {
    hostedRecipe = new AptHostedRecipe(highAvailabilitySupportChecker, new ProxyType(), format);
    proxyRecipe = new AptProxyRecipe(highAvailabilitySupportChecker, new ProxyType(), format);
    when(format.getValue()).thenReturn(APT_NAME);
  }

  @Test
  public void enabledByDefault_AptHostedRepository() {
    when(highAvailabilitySupportChecker.isSupported(APT_NAME)).thenReturn(true);
    assertThat(hostedRecipe.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(APT_NAME);
  }

  @Test
  public void disabledIfNexusIsClusteredAndAptNotCluster_AptHostedRepository() {
    when(highAvailabilitySupportChecker.isSupported(APT_NAME)).thenReturn(false);
    assertThat(hostedRecipe.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(APT_NAME);
  }

  @Test
  public void enabledByDefault_AptProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(APT_NAME)).thenReturn(true);
    assertThat(proxyRecipe.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(APT_NAME);
  }

  @Test
  public void disabledIfNexusIsClusteredAndAptNotCluster_AptProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(APT_NAME)).thenReturn(false);
    assertThat(proxyRecipe.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(APT_NAME);
  }

}
