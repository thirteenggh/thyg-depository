package org.sonatype.nexus.blobstore.restore.maven.internal.orient;

import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.blobstore.restore.RestoreBlobDataSupport;
import org.sonatype.nexus.repository.maven.MavenPath;

/**
 * @since 3.6.1
 */
public final class MavenRestoreBlobData
    extends RestoreBlobDataSupport
{
  private final MavenPath mavenPath;

  public MavenRestoreBlobData(final RestoreBlobData blobData, final MavenPath mavenPath) {
    super(blobData);
    this.mavenPath = mavenPath;
  }

  public MavenPath getMavenPath() {
    return mavenPath;
  }
}
