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

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.sql.OCommandSQL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Remove AssetDownloadCount table
 *
 * @since 3.16
 */
@Named
@Singleton
@Upgrades(model = DatabaseInstanceNames.COMPONENT, from = "1.12", to = "1.13")
public class ComponentDatabaseUpgrade_1_13 extends DatabaseUpgradeSupport
{
  private static final String DB_CLASS = new OClassNameBuilder().type("assetdownloadcount").build();

  private Provider<DatabaseInstance> componentDatabaseInstance;

  @Inject
  public ComponentDatabaseUpgrade_1_13(@Named(DatabaseInstanceNames.COMPONENT) final Provider<DatabaseInstance> componentDatabaseInstance) {
    this.componentDatabaseInstance = checkNotNull(componentDatabaseInstance);
  }

  @Override
  public void apply() throws Exception {
    // drop the old classes - this will also drop any data they currently hold
    withDatabaseAndClass(componentDatabaseInstance, DB_CLASS, this::dropClass);
  }

  private void dropClass(final ODatabaseDocumentTx db, final OClass type) {
    db.command(new OCommandSQL("DROP CLASS " + type.getName() + " IF EXISTS")).execute();
  }
}
