package org.sonatype.nexus.repository.internal.blobstore;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.supportzip.ExportConfigData;
import org.sonatype.nexus.supportzip.ImportData;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

/**
 * Write/Read {@link BlobStoreConfiguration} data to/from a JSON file.
 *
 * @since 3.29
 */
@Named("blobStoreConfigurationExport")
@Singleton
public class BlobStoreConfigurationExport
    extends JsonExporter
    implements ExportConfigData, ImportData
{
  private final BlobStoreConfigurationStore configurationStore;

  @Inject
  public BlobStoreConfigurationExport(final BlobStoreConfigurationStore configurationStore) {
    this.configurationStore = configurationStore;
  }

  @Override
  public void export(final File file) throws IOException {
    log.debug("Export BlobStoreConfiguration data to {}", file);
    List<BlobStoreConfiguration> configurations = configurationStore.list();
    exportToJson(configurations, file);
  }

  @Override
  public void restore(final File file) throws IOException {
    log.debug("Restoring BlobStoreConfiguration data from {}", file);
    importFromJson(file, BlobStoreConfigurationData.class).forEach(configurationStore::create);
  }
}
