package org.sonatype.nexus.repository.content.event.asset;

import org.sonatype.nexus.repository.content.Asset;

/**
 * Event sent whenever an {@link Asset} is updated.
 *
 * @since 3.26
 */
public class AssetUpdatedEvent
    extends AssetEvent
{
  protected AssetUpdatedEvent(final Asset asset) {
    super(asset);
  }
}
