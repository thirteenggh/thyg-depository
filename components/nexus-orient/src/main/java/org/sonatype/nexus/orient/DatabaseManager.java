package org.sonatype.nexus.orient;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.storage.OStorage;

/**
 * Database manager.
 *
 * @since 3.0
 */
public interface DatabaseManager
{
  /**
   * Open a non-pooled connection to the named database.
   *
   * @param name    The name of the database to open.
   * @param create  {@code true} to create the database if it does not exist.
   */
  ODatabaseDocumentTx connect(String name, boolean create);

  /**
   * Access externalizer for a named database.
   */
  DatabaseExternalizer externalizer(String name);

  /**
   * Access named shared database pool.
   *
   * If the pool does not already exist it will be created.
   */
  DatabasePool pool(String name);

  /**
   * Create a new (non-shared) pool for the given named configuration.
   */
  DatabasePool newPool(String name);

  /**
   * Access named database instance.
   *
   * If the instance does not already exist it will be created.
   */
  DatabaseInstance instance(String name);

  /**
   * Updates local pooled connections to use the given storage.
   *
   * @since 3.8
   *
   * @deprecated temporary workaround for https://www.prjhub.com/#/issues/9594
   */
  @Deprecated
  void replaceStorage(OStorage storage);

  /**
   * Gets the configured backup compression level
   *
   * @since 3.18
   */
  int getBackupCompressionLevel();

  /**
   * Gets the configured backup buffer size in bytes
   *
   * @since 3.18
   */
  int getBackupBufferSize();

  /**
   * Gets the configured import buffer size in bytes
   *
   * @since 3.18
   */
  int getImportBufferSize();
}
