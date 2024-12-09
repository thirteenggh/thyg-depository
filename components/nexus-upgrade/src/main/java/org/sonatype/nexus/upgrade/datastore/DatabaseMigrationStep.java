package org.sonatype.nexus.upgrade.datastore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @since 3.29
 */
public interface DatabaseMigrationStep
{
  /**
   * The version this step migrates the database to
   */
  String version();

  /**
   * Perform the migration step. The provided connection should not be closed.
   */
  void migrate(Connection connection) throws Exception;

  /**
   * Indicates whether the migration can occur inside a transaction.
   */
  default boolean canExecuteInTransaction() {
    return true;
  }

  default Integer getChecksum() {
    return null;
  }

  /**
   * Runs the given SQL, returns the number of rows updated or -1 if the result is a set.
   */
  default int runStatement(final Connection connection, final String sql) throws SQLException {
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      if (stmt.execute()) {
        return -1;
      }
      return stmt.getUpdateCount();
    }
  }

  default boolean isH2(final Connection conn) throws SQLException {
    return "H2".equals(conn.getMetaData().getDatabaseProductName());
  }

  default boolean isPostgresql(final Connection conn) throws SQLException {
    return "PostgreSQL".equals(conn.getMetaData().getDatabaseProductName());
  }
}
