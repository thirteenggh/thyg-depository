package org.sonatype.nexus.blobstore.restore;

import java.util.Properties;

import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.blobstore.api.BlobAttributesConstants.HEADER_PREFIX;
import static org.sonatype.nexus.blobstore.api.BlobStore.BLOB_NAME_HEADER;
import static org.sonatype.nexus.blobstore.api.BlobStore.REPO_NAME_HEADER;

/**
 * Simple structure for relevant data for a blob during metadata restoration
 *
 * @since 3.6.1
 */
public class RestoreBlobData
{
  private final Blob blob;

  private final Properties blobProperties;

  private final BlobStore blobStore;

  private final Repository repository;

  public RestoreBlobData(final Blob blob,
                         final Properties blobProperties,
                         final BlobStore blobStore,
                         final RepositoryManager repositoryManager)
  {
    checkNotNull(repositoryManager);
    checkNotNull(blobProperties);

    this.blob = blob;
    this.blobProperties = blobProperties;
    this.blobStore = blobStore;
    this.repository = repositoryManager
        .get(checkNotNull(getProperty(HEADER_PREFIX + REPO_NAME_HEADER), "Blob properties missing repository name"));
  }

  public Blob getBlob() {
    return blob;
  }

  public String getBlobName() {
    return getProperty(HEADER_PREFIX + BLOB_NAME_HEADER);
  }

  public BlobStore getBlobStore() {
    return blobStore;
  }

  public Repository getRepository() {
    return repository;
  }

  public final String getProperty(String propertyName) {
    return blobProperties.getProperty(propertyName);
  }
}
