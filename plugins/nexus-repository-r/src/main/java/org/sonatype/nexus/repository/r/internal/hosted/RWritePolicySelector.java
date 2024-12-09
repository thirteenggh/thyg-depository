package org.sonatype.nexus.repository.r.internal.hosted;

import java.util.Objects;

import org.sonatype.nexus.repository.config.WritePolicy;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.WritePolicySelector;

import static org.sonatype.nexus.repository.config.WritePolicy.ALLOW;
import static org.sonatype.nexus.repository.config.WritePolicy.ALLOW_ONCE;
import static org.sonatype.nexus.repository.r.internal.AssetKind.PACKAGES;
import static org.sonatype.nexus.repository.r.internal.AssetKind.RDS_METADATA;
import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;

public class RWritePolicySelector
    implements WritePolicySelector
{
  @Override
  public WritePolicy select(final Asset asset, final WritePolicy configured) {
    if (ALLOW_ONCE == configured) {
      final String assetKind = asset.formatAttributes().get(P_ASSET_KIND, String.class);
      if (Objects.equals(PACKAGES.name(), assetKind) ||
          Objects.equals(RDS_METADATA.name(), assetKind)) {
        return ALLOW;
      }
    }
    return configured;
  }
}
