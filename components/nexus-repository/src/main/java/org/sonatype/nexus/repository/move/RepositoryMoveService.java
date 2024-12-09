package org.sonatype.nexus.repository.move;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobRef;

/**
 *
 * @since 3.28
 */
public interface RepositoryMoveService
{
  /**
   *
   * @param blobRef
   * @return
   */
  @Nullable
  Blob getIfBeingMoved(BlobRef blobRef, String sourceRepositoryName);
}
