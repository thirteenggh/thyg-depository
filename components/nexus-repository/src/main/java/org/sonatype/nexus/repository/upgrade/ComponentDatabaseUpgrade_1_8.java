package org.sonatype.nexus.repository.upgrade;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;
import org.sonatype.nexus.orient.OClassNameBuilder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Upgrade step to drop the old browse_node schema and data so it can be replaced and rebuilt.
 *
 * @since 3.7
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.COMPONENT, from = "1.7", to = "1.8")
public class ComponentDatabaseUpgrade_1_8
    extends DatabaseUpgradeSupport
{
  private static final String BROWSE_NODE = new OClassNameBuilder().type("browse_node").build();

  private final Provider<DatabaseInstance> componentDatabaseInstance;

  @Inject
  public ComponentDatabaseUpgrade_1_8(
      @Named(DatabaseInstanceNames.COMPONENT) final Provider<DatabaseInstance> componentDatabaseInstance)
  {
    this.componentDatabaseInstance = checkNotNull(componentDatabaseInstance);
  }

  @Override
  public void apply() throws Exception {
    withDatabaseAndClass(componentDatabaseInstance, BROWSE_NODE, (db, table) -> {
      db.getMetadata().getSchema().dropClass(BROWSE_NODE);
    });
  }
}
