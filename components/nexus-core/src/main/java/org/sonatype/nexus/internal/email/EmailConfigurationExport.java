package org.sonatype.nexus.internal.email;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.email.EmailConfiguration;
import org.sonatype.nexus.supportzip.ExportConfigData;
import org.sonatype.nexus.supportzip.ImportData;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

/**
 * Write/Read {@link EmailConfiguration} data to/from a JSON file.
 *
 * @since 3.29
 */
@Named("emailConfigurationExport")
@Singleton
public class EmailConfigurationExport
    extends JsonExporter
    implements ExportConfigData, ImportData
{
  private final EmailConfigurationStore store;

  @Inject
  public EmailConfigurationExport(final EmailConfigurationStore store) {
    this.store = store;
  }

  @Override
  public void export(final File file) throws IOException {
    log.debug("Export EmailConfiguration data to {}", file);
    EmailConfiguration configuration = store.load();
    exportObjectToJson(configuration, file);
  }

  @Override
  public void restore(final File file) throws IOException {
    log.debug("Restoring EmailConfiguration data from {}", file);
    Optional<EmailConfigurationData> configuration = importObjectFromJson(file, EmailConfigurationData.class);
    configuration.ifPresent(store::save);
  }
}
