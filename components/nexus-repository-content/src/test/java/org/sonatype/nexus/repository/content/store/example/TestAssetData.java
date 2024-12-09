package org.sonatype.nexus.repository.content.store.example;

import org.sonatype.nexus.repository.content.store.AssetData;

/**
 * Enhanced test asset.
 */
public class TestAssetData
    extends AssetData
{
  private boolean testFlag;

  public boolean getTestFlag() {
    return testFlag;
  }

  public void setTestFlag(final boolean testFlag) {
    this.testFlag = testFlag;
  }
}
