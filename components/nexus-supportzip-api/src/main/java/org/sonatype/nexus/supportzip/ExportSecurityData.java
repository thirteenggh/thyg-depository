package org.sonatype.nexus.supportzip;

import java.io.File;
import java.io.IOException;

/**
 * Should be used by the Support ZIP to export
 * {@link org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Type#SECURITY} DB data.
 *
 * @since 3.29
 */
public interface ExportSecurityData
{
  /**
   * Export DB data to a Json file.
   *
   * @param file where data will be stored.
   * @throws IOException for any issue during writing a file.
   */
  void export(final File file) throws IOException;
}
