package org.sonatype.nexus.repository.p2.internal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.p2.internal.proxy.P2ProxyRecipe;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.repository.view.handlers.HighAvailabilitySupportChecker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.repository.p2.internal.P2Format.NAME;

public class P2RecipeTest
    extends TestSupport
{
  @Mock
  private P2Format p2Format;

  @Mock
  private HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  private P2ProxyRecipe p2ProxyRecipe;

  @Before
  public void setUp() {
    when(p2Format.getValue()).thenReturn(NAME);
    p2ProxyRecipe = new P2ProxyRecipe(new ProxyType(), p2Format);
    p2ProxyRecipe.setHighAvailabilitySupportChecker(highAvailabilitySupportChecker);
  }

  @Test
  public void haEnabledProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(NAME)).thenReturn(true);
    assertThat(p2ProxyRecipe.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(NAME);
  }

  @Test
  public void haDisabledProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(NAME)).thenReturn(false);
    assertThat(p2ProxyRecipe.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(NAME);
  }
}
