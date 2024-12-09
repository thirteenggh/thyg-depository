package org.sonatype.nexus.blobstore.restore.apt.internal;

import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.RestoreBlobDataSupport;

/**
 * @since 3.17
 */
public class AptRestoreBlobData
    extends RestoreBlobDataSupport
{
  public AptRestoreBlobData(final RestoreBlobData blobData) {
    super(blobData);
  }
}
