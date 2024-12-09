package org.sonatype.nexus.internal.security.realm;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.supportzip.ExportSecurityData;
import org.sonatype.nexus.supportzip.ImportData;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

/**
 * Write/Read {@link RealmConfiguration} data to/from a JSON file.
 *
 * @since 3.29
 */
@Named("realmConfigurationExport")
@Singleton
public class RealmConfigurationExport
    extends JsonExporter
    implements ExportSecurityData, ImportData
{
  private final RealmConfigurationStoreImpl configuration;

  @Inject
  public RealmConfigurationExport(final RealmConfigurationStoreImpl configuration) {
    this.configuration = configuration;
  }

  @Override
  public void export(final File file) throws IOException {
    log.debug("Export RealmConfiguration data to {}", file);
    exportObjectToJson(configuration.load(), file);
  }

  @Override
  public void restore(final File file) throws IOException {
    log.debug("Restoring RealmConfiguration data from {}", file);
    Optional<RealmConfigurationData> realmConfiguration = importObjectFromJson(file, RealmConfigurationData.class);
    realmConfiguration.ifPresent(configuration::save);
  }
}
