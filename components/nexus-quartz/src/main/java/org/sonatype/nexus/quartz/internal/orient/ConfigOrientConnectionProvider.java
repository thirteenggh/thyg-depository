package org.sonatype.nexus.quartz.internal.orient;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.quartz.orient.OrientConnectionProvider;
import org.sonatype.nexus.quartz.orient.OrientQuartzSchema;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import org.quartz.utils.ConnectionProvider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Supplies JDBC connections to Quartz from the OrientDB config database.
 *
 * @since 3.19
 */
@FeatureFlag(name = "nexus.quartz.jobstore.orient")
@Named
@Singleton
public class ConfigOrientConnectionProvider
    extends ComponentSupport
    implements ConnectionProvider
{
  private final Provider<DatabaseInstance> databaseInstance;

  private OrientConnectionProvider orientConnectionProvider;

  @Inject
  public ConfigOrientConnectionProvider(
      @Named(DatabaseInstanceNames.CONFIG) final Provider<DatabaseInstance> databaseInstance)
  {
    this.databaseInstance = checkNotNull(databaseInstance);
  }

  @Override
  public Connection getConnection() throws SQLException {
    return orientConnectionProvider.getConnection();
  }

  @Override
  public void shutdown() throws SQLException {
    if (orientConnectionProvider != null) {
      orientConnectionProvider.shutdown();
      orientConnectionProvider = null;
    }
  }

  @Override
  public void initialize() throws SQLException {
    OrientConnectionProvider connProvider = new OrientConnectionProvider();
    connProvider.setConnectionString(getDatabaseUrl());
    connProvider.setUsePool(false);
    connProvider.initialize();
    orientConnectionProvider = connProvider;
  }

  private String getDatabaseUrl() {
    try (ODatabaseDocumentTx db = databaseInstance.get().acquire()) {
      OrientQuartzSchema.register(db);
      return db.getURL();
    }
  }
}
