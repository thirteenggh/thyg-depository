package org.sonatype.nexus.quartz.internal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.LifecycleSupport;
import org.sonatype.nexus.common.app.BindAsLifecycleSupport;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.quartz.orient.OrientDelegate;

import org.quartz.impl.jdbcjobstore.HSQLDBDelegate;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.impl.jdbcjobstore.PostgreSQLDelegate;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.quartz.spi.JobStore;
import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;

import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.SCHEMAS;

/**
 * {@link JobStore} implementation that uses JDBC.
 *
 * @since 3.19
 */
@FeatureFlag(name = "nexus.quartz.jobstore.jdbc")
@ManagedLifecycle(phase = SCHEMAS)
@Named
@Singleton
@Priority(Integer.MAX_VALUE)
public class JobStoreJdbcProvider
    extends LifecycleSupport
    implements Provider<JobStore>
{
  private static final String QUARTZ_DS = "quartzDS";

  private final ConnectionProvider connectionProvider;

  private volatile JobStore jobStore;

  @Inject
  public JobStoreJdbcProvider(final ConnectionProvider connectionProvider) {
    this.connectionProvider = connectionProvider;
  }

  @Override
  protected void doStop() {
    if (jobStore != null) {
      jobStore.shutdown();
      jobStore = null;
      try {
        connectionProvider.shutdown();
      }
      catch (SQLException e) {
        log.error("Error while stopping job store", e);
      }
    }
  }

  @Override
  public JobStore get() {
    JobStore localRef = jobStore;
    if (localRef == null) {
      synchronized (this) {
        localRef = jobStore;
        if (localRef == null) {
          jobStore = localRef = createJobStore();
        }
      }
    }
    return localRef;
  }

  private JobStore createJobStore() {
    try {
      connectionProvider.initialize();
      DBConnectionManager.getInstance().addConnectionProvider(QUARTZ_DS, connectionProvider);
      JobStoreTX delegate = new JobStoreTX();
      delegate.setDataSource(QUARTZ_DS);
      delegate.setDriverDelegateClass(getDriverDelegateClass());
      return delegate;
    }
    catch (Exception e) {
      log.error("Unable create job store", e);
      return null;
    }
  }

  private String getDatabaseId() throws SQLException {
    try (Connection con = connectionProvider.getConnection()) {
      return con.getMetaData().getDatabaseProductName();
    }
  }

  private String getDriverDelegateClass() throws SQLException {
    switch(getDatabaseId()) {
      case "H2":
        return HSQLDBDelegate.class.getName();
      case "PostgreSQL":
        return PostgreSQLDelegate.class.getName();
      case "OrientDB":
        return OrientDelegate.class.getName();
      default:
        return StdJDBCDelegate.class.getName();
    }
  }

  /**
   * Provider implementations are not automatically exposed under additional interfaces.
   * This small module is a workaround to expose this provider as a (managed) lifecycle.
   */
  @FeatureFlag(name = "nexus.quartz.jobstore.jdbc")
  @Named
  private static class BindAsLifecycle // NOSONAR
      extends BindAsLifecycleSupport<JobStoreJdbcProvider>
  {
    // empty
  }
}
