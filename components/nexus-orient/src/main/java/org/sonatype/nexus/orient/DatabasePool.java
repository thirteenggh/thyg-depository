package org.sonatype.nexus.orient;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

/**
 * Database pool.
 *
 * @since 3.0
 */
public interface DatabasePool
{
  /**
   * Returns the name of this database pool.
   */
  String getName();

  /**
   * Attempt to open a connection to the database.
   */
  ODatabaseDocumentTx acquire();

  /**
   * Returns the number of available connections in the pool.
   *
   * @since 3.14
   */
  int getAvailableCount();

  /**
   * Returns the current number of connections in the pool.
   *
   * @since 3.14
   */
  int getPoolSize();

  /**
   * Close the database pool.
   */
  void close();
}
