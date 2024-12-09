package org.sonatype.nexus.blobstore.restore.maven.internal;

import java.util.Properties;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.restore.RestoreBlobData;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPathParser;

import static com.google.common.base.Preconditions.checkNotNull;

public class MavenRestoreBlobData
    extends RestoreBlobData
{
  private final MavenPath mavenPath;

  public MavenRestoreBlobData(
      final Blob blob,
      final Properties blobProperties,
      final BlobStore blobStore,
      final RepositoryManager repositoryManager,
      final MavenPathParser mavenPathParser)
  {
    super(blob, blobProperties, blobStore, repositoryManager);
    this.mavenPath = checkNotNull(mavenPathParser).parsePath(getBlobName());
  }

  public MavenPath getMavenPath() {
    return mavenPath;
  }
}
