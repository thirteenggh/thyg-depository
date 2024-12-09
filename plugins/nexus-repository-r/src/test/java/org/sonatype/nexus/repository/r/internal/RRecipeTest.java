package org.sonatype.nexus.repository.r.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.r.internal.group.RGroupRecipe;
import org.sonatype.nexus.repository.r.internal.hosted.RHostedRecipe;
import org.sonatype.nexus.repository.r.internal.proxy.RProxyRecipe;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.HostedType;
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
 * @since 1.1.0
 */
public class RRecipeTest
    extends TestSupport
{
  private static final String R_FORMAT = "r";

  @Mock
  private RFormat rFormat;

  @Mock
  private HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  private RHostedRecipe rHostedRecipe;

  private RProxyRecipe rProxyRecipe;

  private RGroupRecipe rGroupRecipe;

  @Before
  public void setUp() {
    when(rFormat.getValue()).thenReturn(R_FORMAT);
    rHostedRecipe = new RHostedRecipe(new HostedType(), rFormat);
    rProxyRecipe = new RProxyRecipe(new ProxyType(), rFormat);
    rGroupRecipe = new RGroupRecipe(new GroupType(), rFormat);
    rHostedRecipe.setHighAvailabilitySupportChecker(highAvailabilitySupportChecker);
    rProxyRecipe.setHighAvailabilitySupportChecker(highAvailabilitySupportChecker);
    rGroupRecipe.setHighAvailabilitySupportChecker(highAvailabilitySupportChecker);
  }

  @Test
  public void haEnabledHostedRepository() {
    when(highAvailabilitySupportChecker.isSupported(R_FORMAT)).thenReturn(true);
    assertThat(rHostedRecipe.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(R_FORMAT);
  }

  @Test
  public void haDisabledHostedRepository() {
    when(highAvailabilitySupportChecker.isSupported(R_FORMAT)).thenReturn(false);
    assertThat(rHostedRecipe.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(R_FORMAT);
  }

  @Test
  public void haEnabledProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(R_FORMAT)).thenReturn(true);
    assertThat(rProxyRecipe.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(R_FORMAT);
  }

  @Test
  public void haDisabledProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(R_FORMAT)).thenReturn(false);
    assertThat(rProxyRecipe.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(R_FORMAT);
  }

  @Test
  public void haEnabledGroupRepository() {
    when(highAvailabilitySupportChecker.isSupported(R_FORMAT)).thenReturn(true);
    assertThat(rGroupRecipe.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(R_FORMAT);
  }

  @Test
  public void haDisabledGroupRepository() {
    when(highAvailabilitySupportChecker.isSupported(R_FORMAT)).thenReturn(false);
    assertThat(rGroupRecipe.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(R_FORMAT);
  }
}
