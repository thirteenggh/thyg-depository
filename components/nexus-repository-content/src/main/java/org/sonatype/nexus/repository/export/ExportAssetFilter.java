package org.sonatype.nexus.repository.export;

import org.sonatype.nexus.repository.content.fluent.FluentAsset;

/**
 * Used to determine assets that should be skipped during export.
 *
 * @since 3.29
 */
public interface ExportAssetFilter
{
  boolean shouldSkipAsset(FluentAsset asset);
}
