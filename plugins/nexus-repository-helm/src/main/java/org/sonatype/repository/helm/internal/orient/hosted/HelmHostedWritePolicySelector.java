package org.sonatype.repository.helm.internal.orient.hosted;

import java.util.Objects;

import org.sonatype.nexus.repository.config.WritePolicy;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.WritePolicySelector;

import static org.sonatype.nexus.repository.config.WritePolicy.ALLOW_ONCE;
import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;
import static org.sonatype.repository.helm.internal.AssetKind.HELM_PACKAGE;

/**
 * @since 3.28
 */
public class HelmHostedWritePolicySelector
    implements WritePolicySelector
{
  @Override
  public WritePolicy select(final Asset asset, final WritePolicy configured) {
    if (ALLOW_ONCE == configured) {
      final String assetKind = asset.formatAttributes().get(P_ASSET_KIND, String.class);
      if (!Objects.equals(HELM_PACKAGE.name(), assetKind)) {
        return WritePolicy.ALLOW;
      }
    }
    return configured;
  }
}
