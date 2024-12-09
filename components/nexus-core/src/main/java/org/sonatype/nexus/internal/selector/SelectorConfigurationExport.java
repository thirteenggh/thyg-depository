package org.sonatype.nexus.internal.selector;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.selector.SelectorConfiguration;
import org.sonatype.nexus.supportzip.ExportConfigData;
import org.sonatype.nexus.supportzip.ImportData;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

/**
 * Write/Read {@link SelectorConfiguration} data to/from a JSON file.
 *
 * @since 3.29
 */
@Named("selectorConfigurationExport")
@Singleton
public class SelectorConfigurationExport
    extends JsonExporter
    implements ExportConfigData, ImportData
{
  private final SelectorConfigurationStore store;

  @Inject
  public SelectorConfigurationExport(final SelectorConfigurationStore store) {
    this.store = store;
  }

  @Override
  public void export(final File file) throws IOException {
    log.debug("Export SelectorConfiguration data to {}", file);
    List<SelectorConfiguration> configurations = store.browse();
    exportToJson(configurations, file);
  }

  @Override
  public void restore(final File file) throws IOException {
    log.debug("Restoring SelectorConfiguration data from {}", file);
    importFromJson(file, SelectorConfigurationData.class).forEach(store::create);
  }
}
