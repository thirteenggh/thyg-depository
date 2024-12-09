package org.sonatype.nexus.cleanup.storage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.cleanup.internal.storage.CleanupPolicyData;
import org.sonatype.nexus.supportzip.ExportConfigData;
import org.sonatype.nexus.supportzip.ImportData;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

/**
 * Write/Read {@link CleanupPolicy} data to/from a JSON file.
 *
 * @since 3.29
 */
@Named("cleanupPolicyExport")
@Singleton
public class CleanupPolicyExport
    extends JsonExporter
    implements ExportConfigData, ImportData
{
  private final CleanupPolicyStorage policyStorage;

  @Inject
  public CleanupPolicyExport(final CleanupPolicyStorage policyStorage) {
    this.policyStorage = policyStorage;
  }

  @Override
  public void export(final File file) throws IOException {
    log.debug("Export CleanupPolicy data to {}", file);
    List<CleanupPolicy> cleanupPolicies = policyStorage.getAll();
    exportToJson(cleanupPolicies, file);
  }

  @Override
  public void restore(final File file) throws IOException {
    log.debug("Restoring CleanupPolicy data from {}", file);
    importFromJson(file, CleanupPolicyData.class).forEach(policyStorage::add);
  }
}
