package org.sonatype.nexus.quartz.internal.store;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.datastore.api.DataStore;

import org.quartz.utils.ConnectionProvider;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.datastore.api.DataStoreManager.CONFIG_DATASTORE_NAME;

/**
 * Supplies JDBC connections to Quartz from the shared config {@link DataStore}.
 *
 * @since 3.19
 */
@FeatureFlag(name = "nexus.datastore.enabled")
@Named
@Singleton
public class ConfigStoreConnectionProvider
    implements ConnectionProvider
{
  private final DataSessionSupplier sessionSupplier;

  @Inject
  public ConfigStoreConnectionProvider(final DataSessionSupplier sessionSupplier) {
    this.sessionSupplier = checkNotNull(sessionSupplier);
  }

  @Override
  public void initialize() {
    // no-op
  }

  @Override
  public Connection getConnection() throws SQLException {
    return sessionSupplier.openConnection(CONFIG_DATASTORE_NAME);
  }

  @Override
  public void shutdown() {
    // no-op
  }
}
