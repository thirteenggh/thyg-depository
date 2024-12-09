package org.sonatype.nexus.repository.pypi.internal.datastore;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.export.ExportAssetFilter;
import org.sonatype.nexus.repository.pypi.internal.PyPiExportAssetFilterSupport;
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat;

/**
 * Filter to exclude indexes from export, as they need to be regenerated on import
 *
 * @since 3.29
 */
@Singleton
@Named(PyPiFormat.NAME)
public class PyPiExportAssetFilter
    extends PyPiExportAssetFilterSupport
    implements ExportAssetFilter
{
  @Override
  public boolean shouldSkipAsset(final FluentAsset asset) {
    return shouldSkipAsset(asset.path(), asset.kind());
  }
}
