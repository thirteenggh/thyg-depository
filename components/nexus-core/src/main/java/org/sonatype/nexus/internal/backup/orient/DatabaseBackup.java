package org.sonatype.nexus.internal.backup.orient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Utilities for doing backups of databases
 *
 * @since 3.2
 */
public interface DatabaseBackup
{

  /**
   * @return java.util.List&lt;String&gt; Names of databases
   */
  List<String> dbNames();

  /**
   * Creates a backup job
   *
   * @param backupFolder Name of folder where backup file will be created
   * @param dbName The name of the database being backed up
   * @param timestamp a timestamp indicating when the backup was taken
   * @return java.util.concurrent.Callable For storing backup data
   * @throws IOException
   */
  Callable<Void> fullBackup(String backupFolder, String dbName, LocalDateTime timestamp) throws IOException;

}
