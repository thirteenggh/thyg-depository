package org.sonatype.nexus.blobstore.api;

import java.util.StringJoiner;

import javax.annotation.Nullable;

/**
 * @since 3.0
 */
public class BlobStoreException
    extends RuntimeException
{
  private final BlobId blobId;

  public BlobStoreException(final String message, @Nullable final BlobId blobId) {
    super(message);
    this.blobId = blobId;
  }

  public BlobStoreException(final String message, final Throwable cause, @Nullable final BlobId blobId) {
    super(message, cause);
    this.blobId = blobId;
  }

  public BlobStoreException(final Throwable cause, @Nullable final BlobId blobId) {
    super(cause);
    this.blobId = blobId;
  }

  /**
   * The BlobId of the blob related to this exception, or {@code null} if there is none.
   */
  @Nullable
  public BlobId getBlobId() {
    return blobId;
  }

  @Override
  public String getMessage() {
    final StringJoiner joiner = new StringJoiner(", ");

    if (blobId != null) {
      joiner.add("BlobId: " + blobId);
    }

    joiner.add(super.getMessage());

    if (getCause() != null && getCause().getMessage() != null) {
      joiner.add("Cause: " + getCause().getMessage());
    }

    return joiner.toString();
  }
}
