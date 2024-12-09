package org.sonatype.nexus.repository.content.event.asset;

import org.sonatype.nexus.repository.content.Asset;

/**
 * Event sent whenever an {@link Asset} is deleted.
 *
 * @since 3.26
 */
public class AssetDeletedEvent
    extends AssetEvent
{
  public AssetDeletedEvent(final Asset asset) {
    super(asset);
  }

  @Override
  public String toString() {
    return "AssetDeletedEvent{} " + super.toString();
  }
}
