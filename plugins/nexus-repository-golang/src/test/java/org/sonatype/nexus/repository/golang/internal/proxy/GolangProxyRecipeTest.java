package org.sonatype.nexus.repository.golang.internal.proxy;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.golang.GolangFormat;
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

public class GolangProxyRecipeTest extends TestSupport
{
  @Mock
  private HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  private String GO_NAME = "go";

  private GolangProxyRecipe proxyUnderTest;

  @Before
  public void setUp() throws Exception {
    proxyUnderTest = new GolangProxyRecipe(highAvailabilitySupportChecker, new ProxyType(), new GolangFormat());
  }

  @Test
  public void enabledByDefault_GoProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(GO_NAME)).thenReturn(true);
    assertThat(proxyUnderTest.isFeatureEnabled(), is(equalTo(true)));
    verify(highAvailabilitySupportChecker).isSupported(GO_NAME);
  }

  @Test
  public void disabledIfNexusIsClusteredAndGoNotCluster_GoProxyRepository() {
    when(highAvailabilitySupportChecker.isSupported(GO_NAME)).thenReturn(false);
    assertThat(proxyUnderTest.isFeatureEnabled(), is(equalTo(false)));
    verify(highAvailabilitySupportChecker).isSupported(GO_NAME);
  }
}
