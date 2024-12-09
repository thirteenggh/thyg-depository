package org.sonatype.nexus.internal.security.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.config.CPrivilege;
import org.sonatype.nexus.security.config.SecurityConfiguration;
import org.sonatype.nexus.supportzip.ExportSecurityData;
import org.sonatype.nexus.supportzip.ImportData;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

/**
 * Write/Read {@link CPrivilege} data to/from a JSON file.
 *
 * @since 3.29
 */
@Named("privilegeExport")
@Singleton
public class PrivilegeExport
    extends JsonExporter
    implements ExportSecurityData, ImportData
{
  private final SecurityConfiguration configuration;

  @Inject
  public PrivilegeExport(final SecurityConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public void export(final File file) throws IOException {
    log.debug("Export CPrivilege data to {}", file);
    List<CPrivilege> cleanupPolicies = configuration.getPrivileges();
    exportToJson(cleanupPolicies, file);
  }

  @Override
  public void restore(final File file) throws IOException {
    log.debug("Restoring CPrivilege data from {}", file);
    importFromJson(file, CPrivilegeData.class).forEach(configuration::addPrivilege);
  }
}
