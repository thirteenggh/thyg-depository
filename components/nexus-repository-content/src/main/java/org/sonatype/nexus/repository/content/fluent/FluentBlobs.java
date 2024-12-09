package org.sonatype.nexus.repository.content.fluent;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.blobstore.api.BlobStoreMetrics;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import com.google.common.hash.HashCode;

/**
 * Fluent API for ingesting blobs.
 *
 * @since 3.21
 */
public interface FluentBlobs
{
  /**
   * Ingests the given stream as a temporary blob with the requested hashing.
   */
  TempBlob ingest(InputStream in, @Nullable String contentType, Iterable<HashAlgorithm> hashing);

  /**
   * Ingests the given payload as a temporary blob with the requested hashing.
   */
  TempBlob ingest(Payload payload, Iterable<HashAlgorithm> hashing);

  /**
   * Ingests a blob from a {@code sourceFile} via hard-linking.
   *
   * @since 3.29
   */
  Blob ingest(Path sourceFile, Map<String, String> headers, HashCode sha1, long size);

  /**
   * Fetches the Blob associated with the specified BlobRef or Optional.empty() if
   * no such Blob exists.
   *
   * @since 3.29
   */
  Optional<Blob> blob(BlobRef blobRef);

  /**
   * @since 3.29
   */
  BlobStoreMetrics getMetrics();
}
