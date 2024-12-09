package org.sonatype.nexus.repository.npm.orient;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.filter.export.OrientExportAssetFilter;
import org.sonatype.nexus.repository.npm.internal.NpmFormat;
import org.sonatype.nexus.repository.storage.Asset;

/**
 * @since 3.25
 */
@Singleton
@Named(NpmFormat.NAME)
public class NpmOrientExportAssetFilter
    implements OrientExportAssetFilter
{
  @Override
  public boolean shouldSkipAsset(final Asset asset) {
    return (!asset.name().endsWith(".tgz"));
  }
}
