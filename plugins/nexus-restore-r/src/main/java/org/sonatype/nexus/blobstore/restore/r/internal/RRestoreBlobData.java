package org.sonatype.nexus.blobstore.restore.r.internal;

import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.RestoreBlobDataSupport;

/**
 * @since 3.28
 */
class RRestoreBlobData extends RestoreBlobDataSupport
{
  RRestoreBlobData(final RestoreBlobData blobData) {
    super(blobData);
  }
}
