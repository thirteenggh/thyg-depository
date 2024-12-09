package org.sonatype.nexus.blobstore.api;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * Provides basic metrics about a BLOB.
 *
 * @since 3.0
 */
public class BlobMetrics
  implements Serializable
{
  private final DateTime creationTime;

  private final String sha1Hash;

  private final long contentSize;

  public BlobMetrics(final DateTime creationTime, final String sha1Hash, final long contentSize) {
    this.creationTime = creationTime;
    this.sha1Hash = sha1Hash;
    this.contentSize = contentSize;
  }

  public DateTime getCreationTime() {
    return creationTime;
  }

  public String getSha1Hash() {
    return sha1Hash;
  }

  public long getContentSize() {
    return contentSize;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "creationTime=" + creationTime +
        ", sha1Hash='" + sha1Hash + '\'' +
        ", contentSize=" + contentSize +
        '}';
  }
}
