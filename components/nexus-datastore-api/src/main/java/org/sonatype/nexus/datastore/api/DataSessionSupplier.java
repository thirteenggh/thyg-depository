package org.sonatype.nexus.datastore.api;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * {@link DataSession} supplier; for use by clients who don't need the full store API.
 *
 * @since 3.19
 */
public interface DataSessionSupplier
{
  /**
   * Opens a new {@link DataSession} against the named data store.
   *
   * @throws DataStoreNotFoundException if the store does not exist
   */
  DataSession<?> openSession(String storeName);

  /**
   * Opens a new JDBC {@link Connection} to the named data store.
   *
   * @throws DataStoreNotFoundException if the store does not exist
   * @throws UnsupportedOperationException if the store doesn't support JDBC
   */
  Connection openConnection(String storeName) throws SQLException;
}
