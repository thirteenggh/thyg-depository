package org.sonatype.nexus.internal.security.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.config.CRole;
import org.sonatype.nexus.security.config.SecurityConfiguration;
import org.sonatype.nexus.supportzip.ExportSecurityData;
import org.sonatype.nexus.supportzip.ImportData;
import org.sonatype.nexus.supportzip.datastore.JsonExporter;

/**
 * Write/Read {@link CRole} data to/from a JSON file.
 *
 * @since 3.29
 */
@Named("roleExport")
@Singleton
public class RoleExport
    extends JsonExporter
    implements ExportSecurityData, ImportData
{
  private final SecurityConfiguration configuration;

  @Inject
  public RoleExport(final SecurityConfiguration configuration) {
    this.configuration = configuration;
  }

  @Override
  public void export(final File file) throws IOException {
    log.debug("Export CRole data to {}", file);
    List<CRole> cleanupPolicies = configuration.getRoles();
    exportToJson(cleanupPolicies, file);
  }

  @Override
  public void restore(final File file) throws IOException {
    log.debug("Restoring CRole data from {}", file);
    importFromJson(file, CRoleData.class).forEach(configuration::addRole);
  }
}
