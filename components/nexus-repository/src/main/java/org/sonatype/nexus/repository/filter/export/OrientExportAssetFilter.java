package org.sonatype.nexus.repository.filter.export;

import org.sonatype.nexus.repository.storage.Asset;

/**
 * @since 3.25
 */
public interface OrientExportAssetFilter
{
  boolean shouldSkipAsset(Asset asset);
}
