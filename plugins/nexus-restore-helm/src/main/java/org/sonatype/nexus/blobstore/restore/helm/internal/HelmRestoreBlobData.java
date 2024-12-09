package org.sonatype.nexus.blobstore.restore.helm.internal;

import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.RestoreBlobDataSupport;

/**
 * @since 3.28
 */
class HelmRestoreBlobData extends RestoreBlobDataSupport
{
  HelmRestoreBlobData(final RestoreBlobData blobData) {
    super(blobData);
  }
}
