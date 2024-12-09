package org.sonatype.nexus.repository.upgrade;

import org.junit.Test;

public class ElasticSearchIndexUpgrade_1_2_Test
    extends ElasticSearchIndexUpgradeTestSupport
{
  @Override
  protected ElasticSearchIndexUpgradeSupport getElasticSearchIndexUpgrade() {
    return new ElasticSearchIndexUpgrade_1_2(appDirs);
  }

  @Test
  public void applyTest() throws Exception {
    assertElasticSearchIndexUpgraded();
  }
}
