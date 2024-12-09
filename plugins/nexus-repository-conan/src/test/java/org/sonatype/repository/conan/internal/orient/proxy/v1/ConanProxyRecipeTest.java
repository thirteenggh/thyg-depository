package org.sonatype.repository.conan.internal.orient.proxy.v1;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.repository.view.handlers.HighAvailabilitySupportChecker;
import org.sonatype.repository.conan.internal.ConanFormat;
import org.sonatype.repository.conan.internal.orient.proxy.v1.ConanProxyApiV1;
import org.sonatype.repository.conan.internal.orient.proxy.v1.ConanProxyRecipe;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConanProxyRecipeTest
    extends TestSupport
{
  @Mock
  private ConanFormat conanFormat;

  @Mock
  private HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  @Mock
  private ConanProxyApiV1 conanApiV1;
  
  private ConanProxyRecipe conanProxyRecipe;

  @Before
  public void setUp() {
    when(conanFormat.getValue()).thenReturn(ConanFormat.NAME);
    conanProxyRecipe = new ConanProxyRecipe(new ProxyType(), conanFormat, conanApiV1);
    conanProxyRecipe.setHighAvailabilitySupportChecker(highAvailabilitySupportChecker);
  }

  @Test
  public void haEnabledProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(ConanFormat.NAME)).thenReturn(true);
    assertThat(conanProxyRecipe.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(ConanFormat.NAME);
  }

  @Test
  public void haDisabledProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(ConanFormat.NAME)).thenReturn(false);
    assertThat(conanProxyRecipe.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(ConanFormat.NAME);
  }
}
