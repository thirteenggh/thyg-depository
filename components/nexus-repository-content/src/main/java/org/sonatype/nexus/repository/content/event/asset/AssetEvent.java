package org.sonatype.nexus.repository.content.event.asset;

import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.store.ContentStoreEvent;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.content.store.InternalIds.contentRepositoryId;

/**
 * Base {@link Asset} event.
 *
 * @since 3.26
 */
public class AssetEvent
    extends ContentStoreEvent
{
  private final Asset asset;

  protected AssetEvent(final Asset asset) {
    super(contentRepositoryId(asset));
    this.asset = checkNotNull(asset);
  }

  public Asset getAsset() {
    return asset;
  }

  @Override
  public String toString() {
    return "AssetEvent{" +
        "asset=" + asset +
        "} " + super.toString();
  }
}
