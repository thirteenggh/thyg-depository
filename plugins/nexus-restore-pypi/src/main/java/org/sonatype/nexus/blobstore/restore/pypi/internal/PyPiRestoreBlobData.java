package org.sonatype.nexus.blobstore.restore.pypi.internal;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.RestoreBlobDataSupport;

/**
 * @since 3.14
 */
public class PyPiRestoreBlobData
    extends RestoreBlobDataSupport
{
  private final String version;

  public PyPiRestoreBlobData(final RestoreBlobData blobData, @Nullable final String version)
  {
    super(blobData);
    this.version = version;
  }

  @Nullable
  public String getVersion() {
    return version;
  }
}
