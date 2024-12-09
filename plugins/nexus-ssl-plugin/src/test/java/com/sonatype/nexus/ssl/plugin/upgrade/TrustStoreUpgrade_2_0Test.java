package com.sonatype.nexus.ssl.plugin.upgrade;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class TrustStoreUpgrade_2_0Test
    extends TestSupport
{
  @Mock
  private LegacyKeyStoreUpgradeService upgradeService;

  @Test
  public void testApply() throws Exception {
    new TrustStoreUpgrade_2_0(upgradeService).apply();
    verify(upgradeService).importKeyStoreFiles();
  }
}
