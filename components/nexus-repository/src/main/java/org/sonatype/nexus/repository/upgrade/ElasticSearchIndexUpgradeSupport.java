package org.sonatype.nexus.repository.upgrade;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.upgrade.Upgrade;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.search.IndexSyncService.INDEX_UPGRADE_MARKER;

/**
 * Upgrading of elasticsearch requires writing to a file. The elasticsearch upgrades
 * now have a common interface so that only a single upgrade will actually run.
 *
 * @since 3.14
 */
public class ElasticSearchIndexUpgradeSupport
    extends ComponentSupport
    implements Upgrade
{
  public static final String NEXUS_LSN = "nexus.lsn";

  private File nexusLsnFile;

  public ElasticSearchIndexUpgradeSupport(final ApplicationDirectories applicationDirectories) {
    checkNotNull(applicationDirectories);
    this.nexusLsnFile = new File(applicationDirectories.getWorkDirectory("elasticsearch"), NEXUS_LSN);
  }

  @Override
  public void apply() throws Exception {
    writeReindexMarker();
  }

  private void writeReindexMarker() throws IOException {
    log.info("Write reindex marker to Elasticsearch marker file");
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream(nexusLsnFile))) {
      out.writeBytes(INDEX_UPGRADE_MARKER);
    }
  }
}
