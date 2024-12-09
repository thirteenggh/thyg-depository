package org.sonatype.nexus.internal.httpclient;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;
import org.sonatype.nexus.supportzip.ExportConfigData;
import org.sonatype.nexus.supportzip.ImportData;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

/**
 * Write/Read {@link HttpClientConfiguration} data to/from a JSON file.
 *
 * @since 3.29
 */
@Named("httpClientConfigurationExport")
@Singleton
public class HttpClientConfigurationExport
    extends JsonExporter
    implements ExportConfigData, ImportData
{
  private final HttpClientConfigurationStore store;

  @Inject
  public HttpClientConfigurationExport(final HttpClientConfigurationStore store) {
    this.store = store;
  }

  @Override
  public void export(final File file) throws IOException {
    log.debug("Export HttpClientConfiguration data to {}", file);
    HttpClientConfiguration configuration = store.load();
    exportObjectToJson(configuration, file);
  }

  @Override
  public void restore(final File file) throws IOException {
    log.debug("Restoring HttpClientConfiguration data from {}", file);
    Optional<HttpClientConfigurationData> configuration = importObjectFromJson(file, HttpClientConfigurationData.class);
    configuration.ifPresent(store::save);
  }
}
