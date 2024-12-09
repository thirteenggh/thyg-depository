package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.blobstore.api.BlobRef;

/**
 * Thrown when attempting to access blob content which is now missing from the blobstore.
 *
 * @since 3.2
 */
public class MissingBlobException
    extends org.sonatype.nexus.repository.MissingBlobException
{
  public MissingBlobException(final BlobRef blobRef) {
    super(blobRef);
  }
}
