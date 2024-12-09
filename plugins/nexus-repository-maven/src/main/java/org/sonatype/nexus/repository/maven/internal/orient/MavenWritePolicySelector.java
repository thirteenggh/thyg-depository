package org.sonatype.nexus.repository.maven.internal.orient;

import java.util.Objects;

import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.config.WritePolicy;
import org.sonatype.nexus.repository.storage.WritePolicySelector;

import static org.sonatype.nexus.repository.maven.internal.Attributes.AssetKind.REPOSITORY_INDEX;
import static org.sonatype.nexus.repository.maven.internal.Attributes.AssetKind.REPOSITORY_METADATA;
import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;
import static org.sonatype.nexus.repository.config.WritePolicy.ALLOW;
import static org.sonatype.nexus.repository.config.WritePolicy.ALLOW_ONCE;

/**
 * Maven specific {@link WritePolicySelector} implementation.
 *
 * @since 3.0
 */
public class MavenWritePolicySelector
    implements WritePolicySelector
{
  /**
   * In case of {@link WritePolicy#ALLOW_ONCE}, write policy for metadata file is overridden to {@link WritePolicy#ALLOW}.
   */
  @Override
  public WritePolicy select(final Asset asset, final WritePolicy configured) {
    if (ALLOW_ONCE == configured) {
      final String assetKind = asset.formatAttributes().get(P_ASSET_KIND, String.class);
      if (Objects.equals(REPOSITORY_METADATA.name(), assetKind) || Objects.equals(REPOSITORY_INDEX.name(), assetKind)) {
        return ALLOW;
      }
    }
    return configured;
  }
}
