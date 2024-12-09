package com.sonatype.nexus.ssl.plugin.upgrade;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class ConfigDatabaseUpgrade_1_1Test
    extends TestSupport
{
  @Mock
  private LegacyKeyStoreUpgradeService upgradeService;

  @Test
  public void testApply() throws Exception {
    new ConfigDatabaseUpgrade_1_1(upgradeService).apply();
    verify(upgradeService).upgradeSchema();
  }
}
