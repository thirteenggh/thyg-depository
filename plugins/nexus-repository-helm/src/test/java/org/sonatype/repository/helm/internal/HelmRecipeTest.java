package org.sonatype.repository.helm.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.repository.view.handlers.HighAvailabilitySupportChecker;
import org.sonatype.repository.helm.internal.orient.hosted.HelmHostedRecipe;
import org.sonatype.repository.helm.internal.orient.proxy.HelmProxyRecipe;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HelmRecipeTest
    extends TestSupport
{

  @Mock
  private HelmFormat helmFormat;

  @Mock
  private HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  private HelmProxyRecipe helmProxyRecipe;

  private HelmHostedRecipe helmHostedRecipe;

  @Before
  public void setUp() {
    when(helmFormat.getValue()).thenReturn(HelmFormat.NAME);
    helmProxyRecipe = new HelmProxyRecipe(new ProxyType(), helmFormat);
    helmHostedRecipe = new HelmHostedRecipe(new HostedType(), helmFormat);
    helmProxyRecipe.setHighAvailabilitySupportChecker(highAvailabilitySupportChecker);
    helmHostedRecipe.setHighAvailabilitySupportChecker(highAvailabilitySupportChecker);
  }

  @Test
  public void haEnabledHostedRepository() {
    when(highAvailabilitySupportChecker.isSupported(HelmFormat.NAME)).thenReturn(true);
    assertThat(helmHostedRecipe.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(HelmFormat.NAME);
  }

  @Test
  public void haDisabledHostedRepository() {
    when(highAvailabilitySupportChecker.isSupported(HelmFormat.NAME)).thenReturn(false);
    assertThat(helmHostedRecipe.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(HelmFormat.NAME);
  }

  @Test
  public void haEnabledProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(HelmFormat.NAME)).thenReturn(true);
    assertThat(helmProxyRecipe.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(HelmFormat.NAME);
  }

  @Test
  public void haDisabledProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(HelmFormat.NAME)).thenReturn(false);
    assertThat(helmProxyRecipe.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(HelmFormat.NAME);
  }
}
