package org.sonatype.nexus.blobstore.api;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;

/**
 * @since 3.4
 */
public interface BlobAttributes
{
  Map<String, String> getHeaders();

  BlobMetrics getMetrics();

  boolean isDeleted();

  void setDeleted(boolean deleted);

  void setDeletedReason(String deletedReason);

  String getDeletedReason();

  /**
   * @since 3.17
   */
  DateTime getDeletedDateTime();

  /**
   * @since 3.17
   */
  void setDeletedDateTime(DateTime deletedDateTime);

  Properties getProperties();

  /**
   * Update attributes based on the {@link BlobAttributes} provided.
   *
   * @since 3.7
   */
  void updateFrom(BlobAttributes blobAttributes);

  /**
   * Stores the attributes in the blob store.
   *
   * @since 3.12
   */
  void store() throws IOException;

}
