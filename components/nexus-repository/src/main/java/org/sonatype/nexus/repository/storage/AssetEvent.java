package org.sonatype.nexus.repository.storage;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.entity.EntityBatchEvent.Batchable;

/**
 * Asset event.
 *
 * @since 3.0
 */
public interface AssetEvent
    extends Batchable
{
  /**
   * @since 3.1
   */
  boolean isLocal();

  String getRepositoryName();

  @Nullable
  EntityId getComponentId();

  EntityId getAssetId();

  Asset getAsset();
}
