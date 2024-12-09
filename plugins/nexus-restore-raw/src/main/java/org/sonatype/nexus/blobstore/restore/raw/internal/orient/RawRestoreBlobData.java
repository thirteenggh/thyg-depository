package org.sonatype.nexus.blobstore.restore.raw.internal.orient;

import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.RestoreBlobDataSupport;

/**
 * @since 3.15
 */
public class RawRestoreBlobData
    extends RestoreBlobDataSupport
{
  public RawRestoreBlobData(final RestoreBlobData blobData) {
    super(blobData);
  }
}
