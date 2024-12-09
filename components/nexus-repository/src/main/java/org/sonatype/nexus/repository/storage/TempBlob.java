package org.sonatype.nexus.repository.storage;

import java.util.Map;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.common.hash.HashAlgorithm;

import com.google.common.hash.HashCode;

/**
 * Blob handle that holds information for a temporary blob used in place of temporary files and streams. Instances must
 * be closed by the caller.
 *
 * @since 3.1
 * @deprecated Use DB-agnostic implementation org.sonatype.nexus.repository.view.payloads.TempBlob
 */
@Deprecated
public class TempBlob
    extends org.sonatype.nexus.repository.view.payloads.TempBlob
{
  public TempBlob(final Blob blob,
                  final Map<HashAlgorithm, HashCode> hashes,
                  final boolean hashesVerified,
                  final BlobStore blobStore)
  {
    super(blob, hashes, hashesVerified, blobStore);
  }
}
