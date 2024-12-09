package org.sonatype.nexus.repository.content.event.asset;

import org.sonatype.nexus.repository.content.Asset;

/**
 * Event sent whenever an {@link Asset} is uploaded.
 *
 * @since 3.26
 */
public class AssetUploadedEvent
    extends AssetUpdatedEvent
{
  public AssetUploadedEvent(final Asset asset) {
    super(asset);
  }

  @Override
  public String toString() {
    return "AssetUploadedEvent{} " + super.toString();
  }
}
