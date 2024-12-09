package org.sonatype.nexus.repository.content.event.asset;

import java.util.Arrays;

import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.store.ContentStoreEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Event sent whenever a large number of {@link Asset}s without components are purged.
 *
 * @since 3.26
 */
public class AssetPurgedEvent
    extends ContentStoreEvent
{
  private final int[] assetIds;

  public AssetPurgedEvent(final int contentRepositoryId, final int[] assetIds) { // NOSONAR
    super(contentRepositoryId);
    this.assetIds = checkNotNull(assetIds);
  }

  public int[] getAssetIds() {
    return assetIds; // NOSONAR
  }

  @Override
  public String toString() {
    return "AssetPurgedEvent{" +
        "assetIds=" + Arrays.toString(assetIds) +
        "} " + super.toString();
  }
}
