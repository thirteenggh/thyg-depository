package org.sonatype.nexus.repository.content;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobRef;

/**
 * Details of the {@link Blob} containing the binary content of an {@link Asset}.
 *
 * Apart from the {@link BlobRef} the rest of these properties are also stored in
 * the blob store, but copies of them are persisted here for performance reasons.
 *
 * @since 3.20
 */
public interface AssetBlob
{
  /**
   * Reference to the blob.
   */
  BlobRef blobRef();

  /**
   * Size of the blob.
   */
  long blobSize();

  /**
   * Content-type of the blob.
   */
  String contentType();

  /**
   * Checksums for the blob.
   *
   * @since 3.24
   */
  Map<String, String> checksums();

  /**
   * When the blob was created.
   */
  OffsetDateTime blobCreated();

  /**
   * The user that triggered creation of this blob; empty if it was an internal request.
   */
  Optional<String> createdBy();

  /**
   * The client IP that triggered creation of this blob; empty if it was an internal request.
   */
  Optional<String> createdByIp();
}
