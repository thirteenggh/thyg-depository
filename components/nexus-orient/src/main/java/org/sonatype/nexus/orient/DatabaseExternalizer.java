package org.sonatype.nexus.orient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Database externalizer.
 *
 * Exposes backup/restore and import/export functionality for a named database.
 *
 * @since 3.0
 */
public interface DatabaseExternalizer
{
  //
  // Backup and Restore
  //

  /**
   * Backup database.  Output format is a compressed ZIP file.
   *
   * @see #restore(InputStream)
   */
  void backup(OutputStream output) throws IOException;

  /**
   * Restore database.
   *
   * @see #backup(OutputStream)
   */
  default void restore(InputStream input) throws IOException {
    restore(input, false);
  }

  /**
   * Restore database.
   *
   * @see #backup(OutputStream)
   * 
   * @since 3.1
   */
  void restore(InputStream input, boolean overwrite) throws IOException;

  //
  // Export and Import
  //

  /**
   * Standard file name inside of database export in instance-specific database directory.
   */
  String EXPORT_FILENAME = "export.json";

  /**
   * Compressed variant of {@link #EXPORT_FILENAME}.
   */
  String EXPORT_GZ_FILENAME = EXPORT_FILENAME + ".gz";

  /**
   * Export database.  Output format is a JSON file.
   *
   * @see #import_(InputStream)
   */
  void export(OutputStream output) throws IOException;

  /**
   * Export database, excluding specified classes.  Output format is a JSON file.
   */
  void export(OutputStream output, Set<String> excludedClassNames) throws IOException;

  /**
   * Import database.
   *
   * @see #export(OutputStream)
   */
  default void import_(InputStream input) throws IOException {
    import_(input, false);
  }

  /**
   * Import database.
   *
   * @see #export(OutputStream)
   * 
   * @since 3.1
   */
  void import_(InputStream input, boolean overwrite) throws IOException;
}
