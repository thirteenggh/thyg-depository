package org.sonatype.nexus.repository.content.facet;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.repository.content.Asset;

/**
 * Something that determines and validates the Content-Type of asset-blobs.
 *
 * @since 3.24
 */
public interface AssetBlobValidator
{
  /**
   * Determines the Content-Type and optionally validates it against the declaration in the blob headers.
   *
   * @return the validated Content-Type
   */
  String determineContentType(Asset asset, Blob blob, boolean strictValidation);
}
