package org.sonatype.nexus.repository.npm.datastore;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.export.ExportAssetFilter;
import org.sonatype.nexus.repository.npm.internal.NpmFormat;

/**
 * @since 3.29
 */
@Singleton
@Named(NpmFormat.NAME)
public class NpmExportAssetFilter
    implements ExportAssetFilter
{
  @Override
  public boolean shouldSkipAsset(final FluentAsset asset) {
    return !asset.path().endsWith(".tgz");
  }
}
