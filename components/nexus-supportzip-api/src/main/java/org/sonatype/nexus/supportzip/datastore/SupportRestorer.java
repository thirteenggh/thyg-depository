package org.sonatype.nexus.supportzip.datastore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.supportzip.ImportData;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Integer.MAX_VALUE;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.RESTORE;

/**
 * Restore {@link org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Type#CONFIG} and
 * {@link org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Type#SECURITY} data from JSON files.
 *
 * @since 3.29
 */
@Named
@Singleton
@Priority(MAX_VALUE)
@ManagedLifecycle(phase = RESTORE)
public class SupportRestorer
    extends StateGuardLifecycleSupport
{
  private static final String FILE_SUFFIX = ".json";

  private final ApplicationDirectories applicationDirectories;

  private final Map<String, ImportData> importDataByName;

  @Inject
  public SupportRestorer(
      final ApplicationDirectories applicationDirectories,
      final Map<String, ImportData> importDataByName)
  {
    this.applicationDirectories = checkNotNull(applicationDirectories);
    this.importDataByName = checkNotNull(importDataByName);
  }

  @Override
  protected void doStart() throws Exception {
    maybeRestore();
  }

  private void maybeRestore() throws IOException {
    Path dbDir = applicationDirectories.getWorkDirectory("db").toPath();
    for (Entry<String, ImportData> importerEntry : importDataByName.entrySet()) {
      String fileName = importerEntry.getKey() + FILE_SUFFIX;
      File file = dbDir.resolve(fileName).toFile();
      ImportData importer = importerEntry.getValue();
      if (file.exists()) {
        importer.restore(file);
      }
      else {
        log.warn("Can't find {} file to restore data", file);
      }
    }
  }
}
