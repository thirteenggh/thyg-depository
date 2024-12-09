package org.sonatype.nexus.repository.upload;

import java.io.InputStream;

import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * Factory for creating {@link TempBlob}
 *
 * @since 3.24
 */
public interface TempBlobFactory {
  TempBlob create(Repository repository, InputStream inputStream, Iterable<HashAlgorithm> hashAlgorithms);

  TempBlob create(Repository repository, Payload payload, Iterable<HashAlgorithm> hashAlgorithms);
}
