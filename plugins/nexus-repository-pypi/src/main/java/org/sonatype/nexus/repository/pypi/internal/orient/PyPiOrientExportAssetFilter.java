package org.sonatype.nexus.repository.pypi.internal.orient;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.filter.export.OrientExportAssetFilter;
import org.sonatype.nexus.repository.pypi.internal.PyPiExportAssetFilterSupport;
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat;
import org.sonatype.nexus.repository.storage.Asset;

import static org.sonatype.nexus.repository.storage.AssetEntityAdapter.P_ASSET_KIND;

/**
 * Filter to exclude indexes from export, as they need to be regenerated on import
 *
 * @since 3.26
 */
@Singleton
@Named(PyPiFormat.NAME)
public class PyPiOrientExportAssetFilter
    extends PyPiExportAssetFilterSupport
    implements OrientExportAssetFilter
{
  @Override
  public boolean shouldSkipAsset(final Asset asset) {
    String assetKindName = asset.formatAttributes().get(P_ASSET_KIND).toString();
    return shouldSkipAsset(asset.name(), assetKindName);
  }
}
