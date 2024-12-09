package org.sonatype.nexus.repository.npm.internal.orient;

import java.util.Objects;

import org.sonatype.nexus.repository.npm.internal.NpmAttributes.AssetKind;

import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.config.WritePolicy;
import org.sonatype.nexus.repository.storage.WritePolicySelector;

import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;

/**
 * npm specific {@link WritePolicySelector} implementation.
 *
 * @since 3.0
 */
public class NpmWritePolicySelector
    implements WritePolicySelector
{
  /**
   * In case of {@link WritePolicy#ALLOW_ONCE}, metadata write policy is overridden to {@link WritePolicy#ALLOW}.
   */
  @Override
  public WritePolicy select(final Asset asset, final WritePolicy configured) {
    if (WritePolicy.ALLOW_ONCE == configured) {
      final String assetKind = asset.formatAttributes().get(P_ASSET_KIND, String.class);
      if (Objects.equals(AssetKind.PACKAGE_ROOT.name(), assetKind)) {
        return WritePolicy.ALLOW;
      }
    }
    return configured;
  }
}
