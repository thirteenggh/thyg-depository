package org.sonatype.nexus.repository.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.sql.OCommandSQL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Upgrade step to set empty assetName rather than null for assetdownloadcount records.
 *
 * @since 3.4
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.COMPONENT, from = "1.5", to = "1.6")
public class ComponentDatabaseUpgrade_1_6 // NOSONAR
    extends DatabaseUpgradeSupport
{
  private static final String UPDATE_ASSET_NAME =
      "update assetdownloadcount set asset_name = '' where asset_name is null";

  private final Provider<DatabaseInstance> componentDatabaseInstance;

  @Inject
  public ComponentDatabaseUpgrade_1_6(
      @Named(DatabaseInstanceNames.COMPONENT) final Provider<DatabaseInstance> componentDatabaseInstance)
  {
    this.componentDatabaseInstance = checkNotNull(componentDatabaseInstance);
  }

  @Override
  public void apply() throws Exception {
    try (ODatabaseDocumentTx db = componentDatabaseInstance.get().connect()) {
      if(db.getMetadata().getSchema().existsClass("assetdownloadcount")){
        int updates = db.command(new OCommandSQL(UPDATE_ASSET_NAME)).execute();
        if (updates > 0) {
          log.info("Updated {} records with null assetIds", updates);
        }
      }
    }
  }
}
