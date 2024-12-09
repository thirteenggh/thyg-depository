package org.sonatype.nexus.repository.upgrade;

import org.junit.Test;

public class ElasticSearchIndexUpgrade_1_1_Test
    extends ElasticSearchIndexUpgradeTestSupport
{
  @Override
  protected ElasticSearchIndexUpgradeSupport getElasticSearchIndexUpgrade() {
    return new ElasticSearchIndexUpgrade_1_1(appDirs);
  }

  @Test
  public void applyTest() throws Exception {
    assertElasticSearchIndexUpgraded();
  }
}
