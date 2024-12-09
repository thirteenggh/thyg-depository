package org.sonatype.nexus.repository.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.repository.search.ElasticSearchIndexCheckpoint;

/**
 * Updates the $data-dir/elasticsearch/nexus.lsn file with a reindex marker string to
 * trigger the IndexSyncService service to update all indexes.
 *
 * @since 3.16
 */
@Named
@Singleton
@Upgrades(model = ElasticSearchIndexCheckpoint.MODEL, from = "1.2", to = "1.3")
public class ElasticSearchIndexUpgrade_1_3
    extends ElasticSearchIndexUpgradeSupport
{
  @Inject
  public ElasticSearchIndexUpgrade_1_3(final ApplicationDirectories applicationDirectories) {
    super(applicationDirectories);
  }
}
