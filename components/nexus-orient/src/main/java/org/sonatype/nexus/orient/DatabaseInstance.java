package org.sonatype.nexus.orient;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

/**
 * Database instance.
 *
 * @since 3.0
 */
public interface DatabaseInstance
{
  /**
   * Returns the name of this database instance.
   */
  String getName();

  /**
   * Open a non-pooled connection to the database.
   */
  ODatabaseDocumentTx connect();

  /**
   * Attempt to open a pooled connection to the database.
   */
  ODatabaseDocumentTx acquire();

  /**
   * Access the {@link DatabaseExternalizer} for the database.
   */
  DatabaseExternalizer externalizer();

  /**
   * Freeze or release database for read-only mode.
   *
   * @since 3.2.1
   */
  void setFrozen(boolean frozen);

  /**
   * @return true if the underlying database is read-only
   * @since 3.5.1
   */
  boolean isFrozen();
}
