package org.sonatype.nexus.repository.apt.internal;

import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.config.WritePolicy;
import org.sonatype.nexus.repository.storage.WritePolicySelector;

/**
 * @since 3.17
 */
public class AptWritePolicySelector
    implements WritePolicySelector
{

  @Override
  public WritePolicy select(final Asset asset, final WritePolicy configured) {
    if (WritePolicy.ALLOW_ONCE == configured) {
      String name = asset.name();
      if (name.endsWith(".deb")) {
        return WritePolicy.ALLOW_ONCE;
      }
      else {
        return WritePolicy.ALLOW;
      }
    }
    return configured;
  }

}
