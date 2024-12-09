package org.sonatype.nexus.repository.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;

import com.orientechnologies.orient.core.metadata.schema.OType;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Add routingRuleId link to Configuration
 *
 * @since 3.17
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.CONFIG, from = "1.6", to = "1.7")
public class ConfigDatabaseUpgrade_1_7 // NOSONAR
    extends DatabaseUpgradeSupport
{
  private static final String DB_NAME = "repository";

  private static final String P_ROUTING_RULE_ID = "routingRuleId";

  private Provider<DatabaseInstance> configDatabaseInstance;

  @Inject
  public ConfigDatabaseUpgrade_1_7(@Named(DatabaseInstanceNames.CONFIG) final Provider<DatabaseInstance> configDatabaseInstance) {
    this.configDatabaseInstance = checkNotNull(configDatabaseInstance);
  }

  @Override
  public void apply() throws Exception {
    withDatabaseAndClass(configDatabaseInstance, DB_NAME, (db, table) -> {
      if (!table.existsProperty(P_ROUTING_RULE_ID)) {
        table.createProperty(P_ROUTING_RULE_ID, OType.LINK);
      }
    });
  }
}
