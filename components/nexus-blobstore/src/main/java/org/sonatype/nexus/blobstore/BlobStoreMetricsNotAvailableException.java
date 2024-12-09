package org.sonatype.nexus.blobstore;

/**
 * @since 3.19
 */
public class BlobStoreMetricsNotAvailableException
    extends Exception
{
  public BlobStoreMetricsNotAvailableException(final Throwable cause) {
    super(cause);
  }
}
